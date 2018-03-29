package chitfund.wayzontech.chitfund.chitfund.model;

public class CollectionReport
{
    private String tokan_no,auction_no,member_name,
                    collection_type,amount,cheque_no,
                    cheque_date,bank_name,receipt_no,receipt_date;

    public CollectionReport() {
    }

    public CollectionReport(String tokan_no, String auction_no, String member_name,
                            String collection_type, String amount, String cheque_no,
                            String cheque_date, String bank_name, String receipt_no,
                            String receipt_date) {
        this.tokan_no = tokan_no;
        this.auction_no = auction_no;
        this.member_name = member_name;
        this.collection_type = collection_type;
        this.amount = amount;
        this.cheque_no = cheque_no;
        this.cheque_date = cheque_date;
        this.bank_name = bank_name;
        this.receipt_no = receipt_no;
        this.receipt_date = receipt_date;
    }

    public String getTokan_no() {
        return tokan_no;
    }

    public void setTokan_no(String tokan_no) {
        this.tokan_no = tokan_no;
    }

    public String getAuction_no() {
        return auction_no;
    }

    public void setAuction_no(String auction_no) {
        this.auction_no = auction_no;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getCollection_type() {
        return collection_type;
    }

    public void setCollection_type(String collection_type) {
        this.collection_type = collection_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getCheque_date() {
        return cheque_date;
    }

    public void setCheque_date(String cheque_date) {
        this.cheque_date = cheque_date;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    public String getReceipt_date() {
        return receipt_date;
    }

    public void setReceipt_date(String receipt_date) {
        this.receipt_date = receipt_date;
    }
}
