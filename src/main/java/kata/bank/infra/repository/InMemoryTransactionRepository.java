package kata.bank.infra.repository;

import kata.bank.domain.AccountId;
import kata.bank.domain.Transaction;
import kata.bank.domain.TransactionIdGenerator;
import kata.bank.domain.TransactionRepository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class InMemoryTransactionRepository implements TransactionRepository, TransactionIdGenerator {

    private static final int MAX_RANGE = 9999999;
    private static final int MIN_RANGE = 1111111;
    private Map<AccountId, List<Transaction>> transactionsByAccount = new HashMap<>();

    @Override
    public long generate() {
        return ThreadLocalRandom.current().ints(MIN_RANGE, MAX_RANGE).distinct().limit(3).sum();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction);
        transactionsByAccount.compute(transaction.getAccountId(), (id, list) -> {
            if(list == null) return Arrays.asList(transaction);
            List<Transaction> transactions = new ArrayList<>(list);
            transactions.add(transaction);
            return transactions;
        });
    }

    @Override
    public List<Transaction> findAccountTransactions(AccountId id) {
        Objects.requireNonNull(id);
        return unmodifiableList(transactionsByAccount.getOrDefault(id, emptyList()));
    }
}
