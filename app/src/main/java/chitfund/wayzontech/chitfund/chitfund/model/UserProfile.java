package chitfund.wayzontech.chitfund.chitfund.model;

public class UserProfile {
    private String username;

    private String SundomainName;

    private String role_id;

    private String user_id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSundomainName() {
        return SundomainName;
    }

    public void setSundomainName(String SundomainName) {
        this.SundomainName = SundomainName;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [username = " + username + ", SundomainName = " + SundomainName + ", role_id = " + role_id + ", user_id = " + user_id + "]";
    }
}
