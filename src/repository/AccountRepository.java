package repository;

import domains.Account;
import domains.Customer;

import java.util.*;

public class AccountRepository {
    private final Map<String,Account> accountsByNumber = new HashMap<>();  //

    public void save(Account account){
        accountsByNumber.put(account.getAccountNumber(),account);  // accountNumber and account ka object
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountsByNumber.values());  // saare account ki list mil gyi
    }

    public Optional<Account> findByNumber(String accountNumber) {  //optional Kyunki ye  Null bhi ho skta hai
        return Optional.ofNullable(accountsByNumber.get(accountNumber));

    }

    public List<Account> findByCustomerId(String customerId) {

        List<Account> result =new ArrayList<>();
        for(Account a : accountsByNumber.values()){
            if(a.getCustomerId().equals(customerId))
                result.add(a);
        }
        return result;
    }
}
