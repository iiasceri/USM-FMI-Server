package fmi.usm.md.mvc.dao.impl;

import fmi.usm.md.mvc.dao.GroupDao;
import fmi.usm.md.mvc.model.Group;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class GroupDaoImpl implements GroupDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<Group> getAllGroups() {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT g FROM Group g", Group.class)
                .getResultList();
    }

    @Override
    public void persist(Group group) {
        sessionFactory.getCurrentSession()
                .persist(group);
    }

    @Override
    public void deleteGroupById(int groupId) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Group g " +
                        "WHERE g.id=:groupId")
                .setParameter("groupId", groupId)
                .executeUpdate();
    }

    @Override
    public void deleteGroupByName(String name) {
        sessionFactory.getCurrentSession()
                .createQuery("DELETE FROM Group g " +
                        "WHERE g.name=:name")
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public Optional<Group> getGroupByName(String name) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT g FROM Group g " +
                        "WHERE g.name=:name", Group.class)
                .setParameter("name", name)
                .getResultList().stream().findFirst();
    }

}
