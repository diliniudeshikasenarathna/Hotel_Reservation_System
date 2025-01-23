package model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    Integer id;
    String userName;
    String password;
    String role;
}
