package fmi.usm.md.mvc.service.impl;

import fmi.usm.md.mvc.dao.GroupDao;
import fmi.usm.md.mvc.model.Group;
import fmi.usm.md.mvc.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupDao groupDao;

    @Override
    public List<Group> getAllGroups() {
        return groupDao.getAllGroups() ;
    }

    @Override
    public void add(Group group) {
        groupDao.persist(group);
    }

    @Override
    public void deleteGroupById(int groupId) {
        groupDao.deleteGroupById(groupId);
    }

    @Override
    public void deleteGroupByName(String groupName) {
        groupDao.deleteGroupByName(groupName);
    }

    @Override
    public Optional<Group> getGroupByName(String name) {
        return groupDao.getGroupByName(name);
    }
}
