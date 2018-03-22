package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 19/3/18.
 */

public class UserLogin
{
    private String id,username,password;

    public UserLogin() {
    }

    public UserLogin(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserLogin(String username) {
        this.username = username;
    }

    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
