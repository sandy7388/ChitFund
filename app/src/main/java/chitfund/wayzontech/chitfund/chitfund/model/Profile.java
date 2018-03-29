package chitfund.wayzontech.chitfund.chitfund.model;

public class Profile
{
    private String name,email,address,birthday,mobile;

    public Profile() {
    }

    public Profile(String name, String email,
                   String address, String birthday,
                   String mobile) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
