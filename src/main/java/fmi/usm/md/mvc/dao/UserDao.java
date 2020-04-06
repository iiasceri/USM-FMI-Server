package fmi.usm.md.mvc.dao;

import fmi.usm.md.mvc.model.Privilege;
import fmi.usm.md.mvc.model.Status;
import fmi.usm.md.mvc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    List<User> getUsersByStatus(Status status);

    List<User> getUsersByPrivilege(Privilege privilege);

    void persist(User user); // method to save registered user in Database

    void deleteUserById(int userId);

    Optional<User> getUserByUsername(String username);
}
