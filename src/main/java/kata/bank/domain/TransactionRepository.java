package kata.bank.domain;

import java.util.List;

public interface TransactionRepository {

    void addTransaction(Transaction transaction);

    List<Transaction> findAccountTransactions(AccountId id);
}
