package models;

public class User {

    private String email;
    private String password;
    private String passwordConfirmation;
    private String passwordHint;

    public User(String email, String password, String passwordConfirmation, String passwordHint) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.passwordHint = passwordHint;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public String getPasswordHint() {
        return passwordHint;
    }
}
