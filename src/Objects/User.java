package Objects;

import java.io.Serializable;

public class User implements Serializable {
    /**
     * Name of the user
     */
    private String name;
    /**
     * Email of the user
     */
    private String email;
    /**
     * Password of the user
     */
    private String password;

    public User(String email, String password, String name) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    //getters
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getName() {
        return this.name;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
}
