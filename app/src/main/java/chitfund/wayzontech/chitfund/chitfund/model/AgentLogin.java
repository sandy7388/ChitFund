package chitfund.wayzontech.chitfund.chitfund.model;

public class AgentLogin
{
    private String id,username,password,member_id,subdomain;

    public AgentLogin() {
    }

    public AgentLogin(String id, String username, String password, String subdomain) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.subdomain = subdomain;
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

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

}
