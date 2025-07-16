package models;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private String passwordConfirmation;
    private String passwordHint;
}

