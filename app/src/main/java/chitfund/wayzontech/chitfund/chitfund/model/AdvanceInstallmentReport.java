package chitfund.wayzontech.chitfund.chitfund.model;

public class AdvanceInstallmentReport
{

    public AdvanceInstallmentReport(String receipt_no, String amount,
                                    String ticket_no, String collection_type,
                                    String member_name, String receipt_date) {
        this.receipt_no = receipt_no;
        this.amount = amount;
        this.ticket_no = ticket_no;
        this.collection_type = collection_type;
        this.member_name = member_name;
        this.receipt_date = receipt_date;
    }

    private String group_name;

    private String bankmaster_id;

    private String receipt_no;

    private String acstatus;

    private String status;

    private String check_id;

    private String group_id;

    private String mode;

    private String member_id;

    private String member_address;

    private String bank_id;

    private String cash_id;

    private String amount;

    private String ac_id;

    private String ticket_no;

    private String gmember_id;

    private String cheque_no;

    private String collection_type;

    private String member_name;

    private String branch_id;

    private String receipt_date;

    private String branch_name;

    public String getGroup_name ()
    {
        return group_name;
    }

    public void setGroup_name (String group_name)
    {
        this.group_name = group_name;
    }

    public String getBankmaster_id ()
    {
        return bankmaster_id;
    }

    public void setBankmaster_id (String bankmaster_id)
    {
        this.bankmaster_id = bankmaster_id;
    }

    public String getReceipt_no ()
    {
        return receipt_no;
    }

    public void setReceipt_no (String receipt_no)
    {
        this.receipt_no = receipt_no;
    }

    public String getAcstatus ()
    {
        return acstatus;
    }

    public void setAcstatus (String acstatus)
    {
        this.acstatus = acstatus;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCheck_id ()
    {
        return check_id;
    }

    public void setCheck_id (String check_id)
    {
        this.check_id = check_id;
    }

    public String getGroup_id ()
    {
        return group_id;
    }

    public void setGroup_id (String group_id)
    {
        this.group_id = group_id;
    }

    public String getMode ()
    {
        return mode;
    }

    public void setMode (String mode)
    {
        this.mode = mode;
    }

    public String getMember_id ()
    {
        return member_id;
    }

    public void setMember_id (String member_id)
    {
        this.member_id = member_id;
    }

    public String getMember_address ()
    {
        return member_address;
    }

    public void setMember_address (String member_address)
    {
        this.member_address = member_address;
    }

    public String getBank_id ()
    {
        return bank_id;
    }

    public void setBank_id (String bank_id)
    {
        this.bank_id = bank_id;
    }

    public String getCash_id ()
    {
        return cash_id;
    }

    public void setCash_id (String cash_id)
    {
        this.cash_id = cash_id;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getAc_id ()
    {
        return ac_id;
    }

    public void setAc_id (String ac_id)
    {
        this.ac_id = ac_id;
    }

    public String getTicket_no ()
    {
        return ticket_no;
    }

    public void setTicket_no (String ticket_no)
    {
        this.ticket_no = ticket_no;
    }

    public String getGmember_id ()
    {
        return gmember_id;
    }

    public void setGmember_id (String gmember_id)
    {
        this.gmember_id = gmember_id;
    }

    public String getCheque_no ()
    {
        return cheque_no;
    }

    public void setCheque_no (String cheque_no)
    {
        this.cheque_no = cheque_no;
    }

    public String getCollection_type ()
    {
        return collection_type;
    }

    public void setCollection_type (String collection_type)
    {
        this.collection_type = collection_type;
    }

    public String getMember_name ()
    {
        return member_name;
    }

    public void setMember_name (String member_name)
    {
        this.member_name = member_name;
    }

    public String getBranch_id ()
    {
        return branch_id;
    }

    public void setBranch_id (String branch_id)
    {
        this.branch_id = branch_id;
    }

    public String getReceipt_date ()
    {
        return receipt_date;
    }

    public void setReceipt_date (String receipt_date)
    {
        this.receipt_date = receipt_date;
    }

    public String getBranch_name ()
    {
        return branch_name;
    }

    public void setBranch_name (String branch_name)
    {
        this.branch_name = branch_name;
    }

    @Override
    public String toString()
    {
        return "AdvanceInstallmentReport [group_name = "+group_name+", bankmaster_id = "+
                bankmaster_id+", receipt_no = "+receipt_no+", acstatus = "+
                acstatus+", status = "+status+", check_id = "+
                check_id+", group_id = "+group_id+", mode = "+
                mode+", member_id = "+member_id+", member_address = "+
                member_address+", bank_id = "+bank_id+", cash_id = "+
                cash_id+", amount = "+amount+", ac_id = "+
                ac_id+", ticket_no = "+ticket_no+", gmember_id = "+
                gmember_id+", cheque_no = "+cheque_no+", collection_type = "+
                collection_type+", member_name = "+member_name+", branch_id = "+
                branch_id+", receipt_date = "+receipt_date+", branch_name = "+
                branch_name+"]";
    }
}
