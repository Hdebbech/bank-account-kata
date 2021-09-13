package kata.bank.domain;

import kata.bank.domain.exception.InsufficientFundsException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private final AccountId id;
    private final BigDecimal balance;

    public Account(AccountId id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Account deposit(BigDecimal amount){
        return new Account(id, balance.add(amount));
    }

    public Account withdraw(BigDecimal amount){
        if(balance.compareTo(amount) < 0){
            throw new InsufficientFundsException();
        }
        return new Account(id, balance.subtract(amount));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
