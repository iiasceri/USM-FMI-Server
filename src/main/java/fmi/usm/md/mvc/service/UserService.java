package fmi.usm.md.mvc.service;

import fmi.usm.md.mvc.dto.UserDto;
import fmi.usm.md.mvc.model.Status;
import fmi.usm.md.mvc.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(int id);

    List<User> getUsersByStatus(Status status);

    boolean validateUser(UserDto userDto);

    void add(User user);

    void deleteUserById(int userId);

    Optional<User> getUserByMail(String mail);

    Optional<User> getUserByUsername(String username);
}
