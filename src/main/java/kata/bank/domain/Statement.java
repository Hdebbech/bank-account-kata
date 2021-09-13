package kata.bank.domain;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public class Statement {

    private final Account account;
    private final List<Transaction> transactions;

    public Statement(Account account, List<Transaction> transactions) {
        this.account = account;
        this.transactions = transactions;
    }

    public List<Transaction> getTransactionsOrderedByEarlierDate() {
        return unmodifiableList(transactions
                .stream()
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList()));

    }

    public Account getAccount() {
        return account;
    }
}
