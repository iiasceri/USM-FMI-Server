package fmi.usm.md.mvc.dto;

import fmi.usm.md.mvc.model.User;

public class UserConverter {

    public static UserDto convert(User user) {
        return new UserDto(user.getMail(), user.getPassword());
    }
}
