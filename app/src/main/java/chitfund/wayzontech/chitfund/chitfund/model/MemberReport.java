package chitfund.wayzontech.chitfund.chitfund.model;

public class MemberReport
{
    private String member_id,member_name,member_mobile;

    public MemberReport(String member_id, String member_name, String member_mobile) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_mobile = member_mobile;
    }

    public MemberReport() {
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_mobile() {
        return member_mobile;
    }

    public void setMember_mobile(String member_mobile) {
        this.member_mobile = member_mobile;
    }
}
