package fmi.usm.md.mvc.controller;

import fmi.usm.md.mvc.model.Group;
import fmi.usm.md.mvc.model.User;
import fmi.usm.md.mvc.service.GroupService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fmi.usm.md.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;

    @RequestMapping(value = "/show-groups", method = GET)
    public String showGroups(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails)
            username = ((UserDetails)principal).getUsername();
        else
            username = principal.toString();

        model.addAttribute("userPrivilege", userService.getUserByUsername(username).get().getPrivilege());
        model.addAttribute("groupList", groupService.getAllGroups());
        return "groups";
    }

    @RequestMapping(value = "/add-group", method = GET)
    public String showGroupForm() {
        return "groups";
    }

    @RequestMapping(value = "/add-group", method = POST)
    public String getGroup(@ModelAttribute("group") Group group){
        if (!group.getName().matches("[a-zA-Z0-9]*"))
            return "redirect:/errorGroup";
        groupService.add(group);
        return "redirect:/show-groups";
    }

    @RequestMapping(value = "/errorGroup", method = GET)
    public String groupsErrorHandler(Model model){

        if (userService.getAllUsers().size() == 0)
            return "login";

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName;
        if (principal instanceof UserDetails)
            userName = ((UserDetails)principal).getUsername();
        else
            userName = principal.toString();
        String uName = userName;

        model.addAttribute("userPrivilege", userService.getUserByUsername(uName).get().getPrivilege());
        model.addAttribute("groupList", groupService.getAllGroups());
        model.addAttribute("error", "Introduceti doar litere/cifre");
        return "groups";
    }

    @RequestMapping(value = "/delete-by-id/{groupId}", method = GET)
    public String removeGroup(@PathVariable int groupId){
        groupService.deleteGroupById(groupId);
        return "redirect:/show-groups";
    }

    @RequestMapping(value = "/schedule-by-name/{name}", method = GET)
    public String showScheduleByGroupName(Model model, @PathVariable(name = "name") String name) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = "";
        if (principal instanceof UserDetails)
            userName = ((UserDetails)principal).getUsername();
        else
            userName = principal.toString();

        model.addAttribute("userPrivilege", userService.getUserByUsername(userName).get().getPrivilege());
        model.addAttribute("groupName", groupService.getGroupByName(name).get().getName());
        model.addAttribute("scheduleType", "weekly");
        return "schedule";
    }

    @RequestMapping(value = "/exam-schedule-by-name/{name}", method = GET)
    public String showExamScheduleByGroupName(Model model, @PathVariable(name = "name") String name) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = "";
        if (principal instanceof UserDetails)
            userName = ((UserDetails)principal).getUsername();
        else
            userName = principal.toString();

        model.addAttribute("userPrivilege", userService.getUserByUsername(userName).get().getPrivilege().toString());
        model.addAttribute("groupName", groupService.getGroupByName(name).get().getName());
        model.addAttribute("scheduleType", "exam");
        return "schedule";
    }

    @Autowired
    ServletContext context;
    @RequestMapping(value = "/save-json/{groupName}/{scheduleType}", method = POST)
    public String saveJSONByGroupName(Model model, @ModelAttribute("json") String json,
                                      @PathVariable(name = "groupName") String groupName,
                                      @PathVariable(name = "scheduleType") String scheduleType) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        String prettyJson = gson.toJson(je);

        String path = context.getRealPath("") + "static/json/" + scheduleType + "/";

        BufferedWriter writer = new BufferedWriter(new FileWriter(path + groupName + ".json"));
        writer.write(prettyJson);
        writer.close();

        if (scheduleType.equals("exam"))
            return "redirect:/exam-schedule-by-name/"+groupName;
        else
            return "redirect:/schedule-by-name/"+groupName;
    }
}
