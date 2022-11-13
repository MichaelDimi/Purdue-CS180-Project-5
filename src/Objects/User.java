package Objects;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* Parent class of Buyers and Sellers.
* contains fields and methods used in 
* both of its child classes.
*
* @author Michael Dimitrov
* @author Federico Lebron
* @author Sanya Mehra
* @author Aaron Ni 
* @author Diya Singh
*/

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

    /**
     * Version of the password that is for display only in account menu
     * Ex: "pas....."
     */
    private String displayPassword;

    public User(String name, String email, String password, String rawPassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.displayPassword = rawPassword.substring(0, 3) + "*".repeat(rawPassword.length()-3);
    }

    public static String hashPassword(String password) {
        // PASSWORD HASHING
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Whoops: Unable to hash password");
            return null;
        }
        md.update(password.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < bytes.length; i++) {
            s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return s.toString();
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

    public String getDisplayPassword() {
        return displayPassword;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password, String rawPassword) {
        this.password = password;
        this.displayPassword = rawPassword.substring(0, 3) + "*".repeat(rawPassword.length()-3);
    }
    public void setName(String name) {
        this.name = name;
    }
}
