package service;

import domains.Account;
import domains.Transactions;
import domains.Type;

import java.util.List;

// service class mai application ka main logic define hota hai
public interface BankService {
    String openAccount(String name,String email,String accountType);
    List<Account> listAccount();
    void deposit(String accountNumber, Double amount, Type type);
    void withdraw(String accountNumber, Double amount, Type type);

    void transfer(String from, String to, Double amount, Type type);

    List<Transactions> getStatement(String account);

    List<Account> searchAccountByCustomerName(String q);
}
