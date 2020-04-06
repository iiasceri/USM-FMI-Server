package fmi.usm.md.mvc.service;

import fmi.usm.md.mvc.model.Group;

import java.util.List;
import java.util.Optional;


public interface GroupService {

    List<Group> getAllGroups();

    void add(Group group);

    void deleteGroupById(int groupId);

    void deleteGroupByName(String name);

    Optional<Group> getGroupByName(String name);

}
