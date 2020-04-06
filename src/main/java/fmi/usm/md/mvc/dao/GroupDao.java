package fmi.usm.md.mvc.dao;

import fmi.usm.md.mvc.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {

    List<Group> getAllGroups();

    void persist(Group group);

    void deleteGroupById(int groupId);

    void deleteGroupByName(String name);

    Optional<Group> getGroupByName(String name);
}
