package app;

import domains.Account;
import domains.Transactions;
import exceptions.ValidationException;
import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;
import java.util.function.Consumer;

import static domains.Type.*;


public class Main {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Welcome to console Bank");
        BankService bankService=new BankServiceImpl();
        boolean running =true;
        while(running) {
            System.out.println("""
                    1) Open Account
                    2) Deposit
                    3) Withdraw
                    4) Transfer
                    5) Account statement
                    6) List Account
                    7) Search account by customer name
                    0) Exit
                    """);

            System.out.println("Choose");
            String choice = sc.nextLine().trim();
            System.out.println("CHOICE :" +choice );
            switch(choice){
                case "0" -> running=false;
                case "1" -> openAccount(sc,bankService);
                case "2" -> deposit(sc,bankService);
                case "3" -> withdraw(sc,bankService);  // -> isko break ki need nhi h ye {java 14+} se aya h good h
                case "4" -> transfer(sc,bankService);
                case "5" -> accountStatement(sc,bankService);
                case "6" -> listAccount(sc, bankService);
                case "7" -> searchAcc(sc,bankService);
            }
        }

    }

    private static void openAccount(Scanner sc,BankService bankService) {  //like int a bese hi
        System.out.println("Customer name ");
        String name = sc.nextLine().trim();

        System.out.println("Customer email");
        String email = sc.nextLine().trim();

        System.out.println("Type of Account : (Saving/Current)");
        String accountType = sc.nextLine().trim();

        System.out.println("Initial Deposit : (Optional ,  Blank for 0 :");
        String amountStr = sc.nextLine().trim();
        if(amountStr.isBlank()) amountStr = "0";
        Double initial= Double.valueOf(amountStr);
        String accountNumber = bankService.openAccount(name,email,accountType);
        if(initial>0){
            bankService.deposit(accountNumber,initial,DEPOSIT);
        }
        System.out.println("Account opened : " + accountNumber);
    }

    private static void deposit(Scanner sc,BankService bankService) {
        System.out.println("Enter Account no");
        String accountNumber = sc.nextLine().trim();
        System.out.println(" Enter amount");
        Double amount= Double.valueOf(sc.nextLine().trim());
        bankService.deposit(accountNumber,amount,DEPOSIT);
        System.out.println("DEPOSITED");

    }

    private static void withdraw(Scanner sc,BankService bankService) {
        System.out.println("Enter Account no");
        String accountNumber = sc.nextLine().trim();
        System.out.println(" Enter amount");
        Double amount= Double.valueOf(sc.nextLine().trim());
        bankService.withdraw(accountNumber,amount,WITHDRAW);
        System.out.println("WITHDRAWN");
    }

    private static void transfer(Scanner sc,BankService bankService) {
        System.out.println("Enter From Account");
        String from = sc.nextLine().trim();
        System.out.println("Enter To Account");
        String to = sc.nextLine().trim();
        System.out.println(" Enter amount");
        Double amount= Double.valueOf(sc.nextLine().trim());
        bankService.transfer(from,to,amount,TRANSFER_OUT);
        System.out.println("TRANSFERED");
    }

    private static void accountStatement(Scanner sc,BankService bankService) {
        System.out.println("Account Number : ");
        String account = sc.nextLine().trim();
        bankService.getStatement(account).forEach(t -> System.out.println(t.getTimestamp() +
                " | " + t.getType()+" | "+ t.getAmount()+" | " + t.getNote()));
    }

    private static void listAccount(Scanner sc ,BankService bankService) {
        bankService.listAccount().forEach(account -> {
            System.out.println(account.getAccountNumber() + " | "+ account.getAccountType() +" | " + account.getBalance() );
        });
    }

    private static void searchAcc(Scanner sc,BankService bankService) {
        System.out.println("Customer name contains : ");
        String q=sc.nextLine().trim();
        bankService.searchAccountByCustomerName(q).forEach(account ->
                System.out.println(account.getAccountNumber() + " | " +
                        account.getAccountType()+ " | " +account.getBalance())
        );
    }
}
