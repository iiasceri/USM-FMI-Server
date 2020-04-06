package fmi.usm.md.mvc.controller;

import fmi.usm.md.mvc.model.*;
import fmi.usm.md.mvc.service.GroupService;
import fmi.usm.md.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserRestController {

    private final UserService userService;
    private final GroupService groupService;

    @RequestMapping(value = "/userNameTaken", method = GET)
    public LinkedHashMap<String, Object> userExists(@RequestParam(name = "username") String username){
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        String lowerCase = username.toLowerCase();
        Optional<User> userOptional = userService.getUserByUsername(lowerCase);

        String boolString;

        if (userOptional.isPresent())
            boolString = "yes";
        else
            boolString = "no";

        map.put("response", boolString);
        return map;
    }

    @RequestMapping(value = "/login", method = POST)
    public LinkedHashMap<String, Object> loginRest(
                                                    @RequestParam(name = "username") String username,
                                                    @RequestParam(name = "password") String password) {

        String hash = "$2a$10$mL0Xwpe8NThYuToTCepO3u";
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        username = username.toLowerCase();

        Optional<User> uo = userService.getUserByUsername(username);
        User user;

        if (uo.isPresent()) {
            user = uo.get();

            String hashPassword = BCrypt.hashpw(password, hash);

            if(user.getPassword().equals(hashPassword)) {
                map.put("status", "success");
                UserJson userJson = new UserJson(user);
                map.put("user", userJson);
                return map;
            }
        }

        map.put("status", "fail");
        map.put("message", "Credentials are incorrect");
        return map;
    }

    @RequestMapping(value = "/register", method = POST)
    public LinkedHashMap<String, Object> registerRest(
                                                        @RequestParam(value = "username") String username,
                                                        @RequestParam(value = "mail") String mail,
                                                        @RequestParam(value = "familyName") String familyName,
                                                        @RequestParam(value = "password") String password,
                                                        @RequestParam(value = "gender") String gender,
                                                        @RequestParam(value = "groupName") String groupName,
                                                        @RequestParam(value = "subGroup") String subGroup) {

        String hash = "$2a$10$mL0Xwpe8NThYuToTCepO3u";
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        username = username.toLowerCase();

        User user = new User();
        Group g;

        if (subGroup != null) {
            if (subGroup.equals("I"))
                user.setSubGroup(SubGroup.I);
            else if (subGroup.equals("II"))
                user.setSubGroup(SubGroup.II);
            else if (subGroup.equals("Fara"))
                user.setSubGroup(SubGroup.Fara);
            else {
                map.put("status", "fail");
                map.put("message", "Non existing subGroup type: " + subGroup);
            }
        }
        else {
            map.put("status", "fail");
            map.put("message", "subGroup is null");
        }

        if (familyName != null) {
            familyName = familyName.replaceAll("%20", " ");
            user.setFamilyname(familyName);
        }
        else {
            map.put("status", "fail");
            map.put("message", "Family name is null!");
            return map;
        }

        if (gender != null) {
            if (gender.equals("male")) {
                user.setGender(Gender.MALE);
            }
            else if (gender.equals("female")) {
                user.setGender(Gender.FEMALE);
            }
            else {
                map.put("status", "fail");
                map.put("message", "Male or female choose one, u gave: " + gender);
                return map;
            }
        }
        else {
            map.put("status", "fail");
            map.put("message", "Are u human ? (gender is null) wut?");
            return map;
        }

        if (groupName != null) {
            Optional<Group> go = groupService.getGroupByName(groupName);

            if (go.isPresent()) {
                g = go.get();
            }
            else {
                map.put("status", "fail");
                map.put("message", "Specified groupName doesnt exist!");
                return map;
            }
            user.setGroup(g);
        }
        else {
            map.put("status", "fail");
            map.put("message", "groupName is null!");
            return map;
        }


        Optional<User> tmpO = userService.getUserByUsername(username);

        if (tmpO.isPresent()) {
            map.put("status", "fail");
            map.put("message", "User with username '"+ username + "' already exists!");
            return map;
        }

        user.setUsername(username);
        user.setMail(mail);
        user.setPassword(BCrypt.hashpw(password, hash));

        map.put("status", "success");
        userService.add(user);
        UserJson userJson = new UserJson(user);
        map.put("user", userJson);
        return map;
    }

    @RequestMapping(value = "/test", method = GET)
    public LinkedHashMap<String, Object> testRestGET() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("status", "success");
        map.put("message", "get");
        return map;
    }

    @RequestMapping(value = "/test", method = POST)
    public LinkedHashMap<String, Object> testRestPOST() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        map.put("status", "success");
        map.put("message", "post");
        return map;
    }
}
