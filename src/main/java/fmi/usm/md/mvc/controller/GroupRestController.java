package fmi.usm.md.mvc.controller;

import fmi.usm.md.mvc.model.Group;
import fmi.usm.md.mvc.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class GroupRestController {

    private final GroupService groupService;

    @RequestMapping(value = "/getGroupNames", method = GET)
    public LinkedHashMap<String, Object> loginRest() {

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        List<String> groupNameList = new ArrayList<>();

        List<Group> groupList = groupService.getAllGroups();

        for (Group g : groupList) {
            groupNameList.add(g.getName());
        }

        map.put("status", "success");
        map.put("groupNames", groupNameList);
        return map;
    }
}
