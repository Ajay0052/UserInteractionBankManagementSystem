package domains;

import java.time.LocalDateTime;

public class Transactions {
    private String Id;  //Transaction Id
    private Type type;   // now object of enum is like a datatype
    private String accountNumber;
    private Double amount;
    private LocalDateTime timestamp;
    private String note;

    public Transactions(String Id, Type type, String accountNumber, Double amount, LocalDateTime timestamp, String note) {
        this.Id = Id;
        this.type = type;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.timestamp = timestamp;
        this.note = note;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
