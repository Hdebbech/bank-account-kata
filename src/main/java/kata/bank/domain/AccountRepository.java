package kata.bank.domain;

import java.util.Optional;

public interface AccountRepository {

    void saveAccount(Account account);

    Optional<Account> findAccount(AccountId accountId);

    void updateAccount(Account account);
}
