package PM.login.model;

/**
 *
 * @author andreendo
 */
public class User {
    private String username, password;
    private UserType type;
    
    public User(String username, String password, UserType type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public UserType getType() {
        return type;
    }   
}
