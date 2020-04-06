package fmi.usm.md.mvc.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class UserJson {
    private String username;
    private String mail;
    private String familyName;
    private String gender;
    private String groupName;
    private String subGroup;

    public UserJson(User user) {
        username = user.getUsername();
        mail = user.getUsername();
        familyName = user.getFamilyname();
        gender = user.getGender().toString().toLowerCase();
        groupName = user.getGroup().getName();
        subGroup = user.getSubGroup().toString();
    }
}
