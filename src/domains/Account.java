package domains;

public class Account {
    private String accountNumber;  //these all are constructor calls
    private String customerId;
    private String accountType;
    private Double balance;
  //right click -> generate -> constructor -> ctrl+A -> OK  CONSTRUCTOR CREATED
    public Account(String accountNumber, String customerId, String accountType, Double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this. accountType=  accountType;
        this.balance = balance;
        }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return  accountType;
    }

    public void setAccountType(String accountType) {
        this. accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


}

