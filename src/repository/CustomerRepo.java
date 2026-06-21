package repository;

import domains.Account;
import domains.Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepo {
    private final Map<String, Customer>  customersById=new HashMap<>();

    public List<Customer> findAll() {
        return new ArrayList<>(customersById.values());
    }

    public void save(Customer c) {
        customersById.put(c.getId(),c);
    }
}
