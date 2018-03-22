package chitfund.wayzontech.chitfund.chitfund.model;

/**
 * Created by sandy on 22/3/18.
 */

public class LastAuction
{
    private String date,amount,lock_amount,group_name,received_by;

    public LastAuction() {
    }

    public LastAuction(String date, String amount,
                       String lock_amount, String group_name,
                       String received_by) {
        this.date = date;
        this.amount = amount;
        this.lock_amount = lock_amount;
        this.group_name = group_name;
        this.received_by = received_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLock_amount() {
        return lock_amount;
    }

    public void setLock_amount(String lock_amount) {
        this.lock_amount = lock_amount;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getReceived_by() {
        return received_by;
    }

    public void setReceived_by(String received_by) {
        this.received_by = received_by;
    }
}
