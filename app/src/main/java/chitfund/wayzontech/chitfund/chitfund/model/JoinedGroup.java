package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 24/3/18.
 */

public class JoinedGroup
{
    private String group_id,group_name,amount;

    public JoinedGroup() {
    }

    public JoinedGroup(String group_id, String group_name,
                       String amount) {
        this.group_id = group_id;
        this.group_name = group_name;

        this.amount = amount;
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
