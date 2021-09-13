package kata.bank.domain;

import java.util.Objects;

public class AccountId {

    private final String iban;

    private AccountId(String iban) {
        this.iban = iban;
    }

    public static AccountId from(String iban){
        validate(iban);
        return new AccountId(iban);
    }

    private static void validate(String iban){
        Objects.requireNonNull(iban);
        if(iban.isBlank()){
            throw new IllegalArgumentException();
        }
    }

    public String getIban() {
        return iban;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountId accountId = (AccountId) o;
        return iban.equals(accountId.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban);
    }

    @Override
    public String toString() {
        return "iban='" + iban + '\'' ;
    }
}
