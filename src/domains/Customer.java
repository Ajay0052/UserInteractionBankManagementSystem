package domains;

public class Customer {
    private String Id;
    private String customerName;
    private String email;

    public Customer(String id, String customerName, String email) {
        Id = id;
        this.customerName = customerName;
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
