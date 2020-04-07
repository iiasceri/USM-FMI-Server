package fmi.usm.md.mvc.controller;

import fmi.usm.md.mvc.model.*;
import fmi.usm.md.mvc.service.GroupService;
import fmi.usm.md.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/")
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    @RequestMapping(value = {"/", "/login"}, method = GET)
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/show-users", method = POST)
    public String showUsersByStatusForm(Model model, @RequestParam(name = "status") String status) {
        if (status == null || status.equals("") || !Arrays.stream(Status.values()).map(Enum::toString).collect(Collectors.toList()).contains(status)) {
            model.addAttribute("error", "Enter a valid status");
            return "hello";
        }
        model.addAttribute("status", status);
        model.addAttribute("userList", userService.getUsersByStatus(Status.valueOf(status)));
        return "hello";
    }

    @RequestMapping(value = "/show-users", method = GET)
    public String showUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/show-user/by-id/{id}", method = GET)
    public String showUserById(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("userById", userService.getUserById(id));
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/show-users/by-status", method = GET)
    public String showUsersByStatus(Model model, @RequestParam(name = "status") String status) {
        model.addAttribute("userList", userService.getUsersByStatus(Status.valueOf(status)));
        return "users";
    }

    @RequestMapping(value = "/register", method = GET)
    public String showRegisterPage(Model model, @ModelAttribute("user") User user) {
        List<Group> groupList = groupService.getAllGroups();
        boolean isGroupListEmpty = false;
        if (groupList.size() == 0)
            isGroupListEmpty = true;
        model.addAttribute("isGroupListEmpty", isGroupListEmpty);
        model.addAttribute("groupList", groupList);
        return "register";
    }

    @RequestMapping(value = "/register", method = POST)
    public String takeRegisterValues(@ModelAttribute("user") User user,
                                     @RequestParam(value = "groupName") String groupName,
                                     @RequestParam(value = "subGroup") String subGroup) {

        String hash = "$2a$10$mL0Xwpe8NThYuToTCepO3u";

        //Security Validations:
        if (!user.getUsername().matches("[a-zA-Z0-9]*"))
            return "redirect:/errorRegisterName";
        if (!EmailValidator.getInstance().isValid(user.getMail()))
            return "redirect:/errorRegisterMail";

        user.setUsername(user.getUsername().toLowerCase());
        if (groupName != null) {
            Optional<Group> go = groupService.getGroupByName(groupName);
            Group g = new Group();
            if (go.isPresent()) {
                g = go.get();
            }
            user.setGroup(g);
        }

        if (subGroup != null) {
            if (subGroup.equals("I"))
                user.setSubGroup(SubGroup.I);
            else if (subGroup.equals("II"))
                user.setSubGroup(SubGroup.II);
            else if (subGroup.equals("Fara"))
            user.setSubGroup(SubGroup.Fara);
        }
        user.setPassword(BCrypt.hashpw(user.getPassword(), hash));
        if (userService.getAllUsers().size() == 0)
            user.setPrivilege(Privilege.ADMIN);
        userService.add(user);
        return "redirect:/show-groups";
    }

    @RequestMapping(value = "/errorRegisterName", method = GET)
    public String registerErrorHandlerName(Model model){
        model.addAttribute("error", "Introduceti doar litere/cifre in campurile cu Nume");
        model.addAttribute("groupList", groupService.getAllGroups());
        return "/register";
    }

    @RequestMapping(value = "/errorRegisterMail", method = GET)
    public String registerErrorHandlerMail(Model model){
        model.addAttribute("error", "Introduceti doar litere/cifre si '@ .com, .md' pentru Mail");
        model.addAttribute("groupList", groupService.getAllGroups());
        return "/register";
    }

    @RequestMapping(value = "/json", method = GET)
    @ResponseBody
    public List<User> getJsonUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = GET)
    @ResponseBody
    public User showUserById(@PathVariable(name = "id") int id) {
        return userService.getUserById(id).get();
    }

    @RequestMapping(value = "/user/delete-by-id/{userId}", method = GET)
    public String removeUser(@PathVariable int userId, Model model){
        userService.deleteUserById(userId);
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @RequestMapping(value = "/error", method = GET)
    public String loginErrorHandler(Model model){
        model.addAttribute("error", "Datele introduse nu sunt valide");
        return "login";
    }

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    //    @RequestMapping(value = "/show-users/{gender}", method = GET)
    //    public String showUsersByGender(Model model, @PathVariable(name = "gender") String gender) {
    //        model.addAttribute("userList", userService.getUsersByGender(Gender.valueOf(gender)));
    //        return "users";
    //    }
    //
    //    @RequestMapping(value = "/show-users/by-gender", method = GET)
    //    public String showUsersByGenderForm(Model model, @RequestParam(name = "gender") String gender) {
    //        model.addAttribute("userList", userService.getUsersByGender(Gender.valueOf(gender)));
    //        return "users";
    //    }
}