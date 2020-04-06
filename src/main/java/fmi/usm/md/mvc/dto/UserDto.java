package fmi.usm.md.mvc.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {

    private String username;
    private String password;

}
