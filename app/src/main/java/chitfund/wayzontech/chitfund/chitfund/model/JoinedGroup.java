package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroup
{
    private String group_id, group_name, amount, next_date, time, type;

    public JoinedGroup() {
    }

    public JoinedGroup(String group_id, String group_name,
                       String amount, String next_date, String time) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.next_date = next_date;
        this.amount = amount;
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNext_date() {
        return next_date;
    }

    public void setNext_date(String next_date) {
        this.next_date = next_date;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
