package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OnboardForm {
    private String username;
    private String pass;
    private String email;

    // NOTE: for deserialization purposes, i.e., generating an object from a request via the @RequestBody annotation,
    // a domain object must implement a zero-argument constructor.
    // public OnboardForm() {}

    // Alternatively, you can use the JsonCreator annotation (not sure how this works).
    @JsonCreator
    public OnboardForm(@JsonProperty("username") String username,
                       @JsonProperty("password") String password,
                       @JsonProperty("email") String email) {
        this.username = username;
        this.pass = password;
        this.email = email;
    }

    // As per usge, you need an accessor for each member variable.
    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getUsername() {
        return username;
    }
}
