package fmi.usm.md.mvc.service.impl;

import fmi.usm.md.mvc.dao.UserDao;
import fmi.usm.md.mvc.dto.UserDto;
import fmi.usm.md.mvc.model.Privilege;
import fmi.usm.md.mvc.model.Status;
import fmi.usm.md.mvc.model.User;
import fmi.usm.md.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    @Override
    public List<User> getUsersByStatus(Status status) {
        return userDao.getUsersByStatus(status);
    }

//    @Override
//    public List<User> getUsersByGender(Gender gender) {
//        //FIXME: review total user list and create new DAO method
//        return userDao.getAllUsers().stream()
//                .filter( u -> u.getGender() == gender)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public boolean validateUser(UserDto userDto) {
        return userDao.getAllUsers().stream()
                .anyMatch(u -> userDto.getUsername().equals(u.getUsername()) && userDto.getPassword().equals(u.getPassword()));
    }

    @Override
    public void add(User user) {
        userDao.persist(user);
    }

    @Override
    public void deleteUserById(int userId) {
        userDao.deleteUserById(userId);
    }

    @Override
    public Optional<User> getUserByMail(String mail) {
        return userDao.getAllUsers().stream()
                .filter(u -> mail.equals(u.getMail()))
                .findFirst();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
}
