package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 19/3/18.
 */

public class UserLogin
{
    private String id,username,password,member_id;

    public UserLogin() {
    }

    public UserLogin(String id, String username, String password, String member_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.member_id = member_id;
    }

    public UserLogin(String username) {
        this.username = username;
    }


    public UserLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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
