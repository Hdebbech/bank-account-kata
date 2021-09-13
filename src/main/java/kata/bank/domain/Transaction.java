package kata.bank.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private final long reference;
    private final AccountId accountId;
    private final Type type;
    private final LocalDateTime date;
    private final BigDecimal amount;
    private final BigDecimal balance;

    public Transaction(long reference, AccountId accountId, Type type, LocalDateTime date, BigDecimal amount, BigDecimal balance) {
        this.reference = reference;
        this.accountId = accountId;
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.balance = balance;
    }

    public static Builder builder(){
        return new Builder();
    }

    public long getReference() {
        return reference;
    }

    public AccountId getAccountId() {
        return accountId;
    }

    public Type getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return reference == that.reference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    public enum Type {
        WITHDRAWAL, DEPOSIT
    }


    public static class Builder {

        private long reference;
        private AccountId accountId;
        private Type type;
        private LocalDateTime date;
        private BigDecimal amount;
        private BigDecimal balance;


        public Builder withType(Type type){
            this.type = type;
            return this;
        }

        public Builder withReference(long reference){
            Objects.requireNonNull(reference);
            this.reference = reference;
            return this;
        }

        public Builder withAccountId(AccountId accountId){
            Objects.requireNonNull(accountId);
            this.accountId = accountId;
            return this;
        }

        public Builder withDate(LocalDateTime date){
            Objects.requireNonNull(date);
            this.date = date;
            return this;
        }

        public Builder withAmount(BigDecimal amount){
            Objects.requireNonNull(amount);
            this.amount = amount;
            return this;
        }

        public Builder withBalance(BigDecimal balance){
            Objects.requireNonNull(balance);
            this.balance = balance;
            return this;
        }

        public Transaction build(){
            return new Transaction(reference, accountId, type, date, amount, balance);
        }

    }
}
