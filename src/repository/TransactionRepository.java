package repository;

import domains.Transactions;

import java.util.*;

public class TransactionRepository {
    private final Map<String , List<Transactions>> txByAccount = new HashMap<>();  // AccountNo and ListOfTxns

    public void add(Transactions txn) {
        txByAccount.computeIfAbsent(txn.getAccountNumber(),k -> new ArrayList<>()).add(txn); // first txn bhi ho skti hai
    }

    public List<Transactions> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account, Collections.emptyList()));
    }
}
