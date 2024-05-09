package ca.javau9.tripplanner.payload.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateRequest {
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UpdateRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
