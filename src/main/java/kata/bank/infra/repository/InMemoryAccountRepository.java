package kata.bank.infra.repository;

import kata.bank.domain.Account;
import kata.bank.domain.AccountId;
import kata.bank.domain.AccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class InMemoryAccountRepository implements AccountRepository {

    private Map<AccountId, Account> accounts = new HashMap<>();

    @Override
    public void saveAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    @Override
    public Optional<Account> findAccount(AccountId accountId) {
        requireNonNull(accountId);
        return Optional.ofNullable(accounts.get(accountId));
    }

    @Override
    public void updateAccount(Account account) {
        requireNonNull(account);
        accounts.computeIfPresent(account.getId(), (k, v)-> account);
    }
}
