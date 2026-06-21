package service.impl;

import domains.Account;
import domains.Customer;
import domains.Transactions;
import domains.Type;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientfundExceptions;
import exceptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepo;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

import java.time.LocalDateTime;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static domains.Type.DEPOSIT;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository=new AccountRepository();
    private final TransactionRepository transactionRepository=new TransactionRepository();
    private final CustomerRepo customerRepo=new CustomerRepo();

    private final Validation<String> validateName=name -> {
        if(name==null || name.isBlank()) throw new ValidationException("Name is Required");
    };

    private final Validation<String> validateEmail=email -> {
        if(email==null || !email.contains("@")) throw new ValidationException("Email is Required");
    };
    private final Validation<String> validateAccountType=type -> {
        if(type==null || !(type.equalsIgnoreCase("SAVINGS")) || !type.equalsIgnoreCase("CURRENT") )
            throw new ValidationException("SAVING / CURRENT is needed");
    };
    private final Validation<Double> validateAmountPositive=amount -> {
        if(amount==null || amount<0 ) throw new ValidationException("Please enter valid amount");
    };

    @Override
    public String openAccount(String name, String email, String accountType) {

        validateName.validate(name);
        validateEmail.validate(email);
        validateAccountType.validate(accountType);


        String customerId = UUID.randomUUID().toString();
        //create customer

        Customer c=new Customer(customerId,name,email);
        customerRepo.save(c);

        // CHANGE LATER --> 10 + 1 = AC11
       // String accountNumber = UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();
        Account account=new Account(accountNumber,customerId,accountType, (double) 0); // account mai sbhi value bhj di
        // SAVE
        accountRepository.save(account);
        return accountNumber;
    }
                private String getAccountNumber() {
                    int size=accountRepository.findAll().size()+1;   //yaha se bas size leke aata hai
                    return String.format("AC%06d",size);
                }

    @Override
    public List<Account> listAccount() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account:: getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, Type type) {
        validateAmountPositive.validate(amount);
        Account account=accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found " + accountNumber));
                account.setBalance(account.getBalance()+(amount));

                Transactions txn= new Transactions(UUID.randomUUID().toString(),DEPOSIT,accountNumber,amount, LocalDateTime.now(),"Amount Deposited");
                transactionRepository.add(txn);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, Type type) {
        validateAmountPositive.validate(amount);
        Account account=accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(" Account Not Found!" + accountNumber));
        if(account.getBalance().compareTo(amount)<0) throw new RuntimeException("Insufficient Balance");
        account.setBalance(account.getBalance()-amount);

        Transactions txn= new Transactions(UUID.randomUUID().toString(),Type.WITHDRAW,
                accountNumber,amount, LocalDateTime.now(),"Amount withdrawn");
        transactionRepository.add(txn);
    }

    @Override
    public void transfer(String from, String to, Double amount, Type type) {
        validateAmountPositive.validate(amount);
        if(from.equals(to)) throw new ValidationException("Cannot Transfer to your own account");
        Account fromAcc=accountRepository.findByNumber(from)
                .orElseThrow(() -> new AccountNotFoundException(" Account Not Found!" + from));
        Account toAcc=accountRepository.findByNumber(to)
                .orElseThrow(() -> new AccountNotFoundException(" Account Not Found!" + to));

        if(fromAcc.getBalance().compareTo(amount)<0) throw new InsufficientfundExceptions("Insufficient Balance");
        fromAcc.setBalance(fromAcc.getBalance()-amount);
        toAcc.setBalance(toAcc.getBalance()+amount);

        Transactions out=new Transactions(UUID.randomUUID().toString(),Type.TRANSFER_OUT,from
                ,amount, LocalDateTime.now(),"Amount transfered");
        transactionRepository.add(out);


        Transactions in=new Transactions(UUID.randomUUID().toString(),Type.TRANSFER_IN,to
                ,amount, LocalDateTime.now(),"Amount Received");
        transactionRepository.add(in);

    }

    @Override
    public List<Transactions> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transactions::getTimestamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByCustomerName(String q) {
        String query=(q==null) ? "":q.toLowerCase();
//        List<Account> result =new ArrayList<>();
//        for(Customer c : customerRepo.findAll()){
//            if(c.getCustomerName().toLowerCase().contains(query))
//                result.addAll(accountRepository.findByCustomerId(c.getId()));
//        }
//        result.sort(Comparator.comparing(Account:: getAccountNumber));
//        return result;

        return customerRepo.findAll().stream()
                .filter(c -> c.getCustomerName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getId()).stream())
                .sorted(Comparator.comparing(Account:: getAccountNumber))
                .collect(Collectors.toList());

    }


}
