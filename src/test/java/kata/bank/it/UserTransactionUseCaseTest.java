package kata.bank.it;

import kata.bank.domain.Account;
import kata.bank.domain.AccountId;
import kata.bank.domain.Transaction;
import kata.bank.infra.repository.InMemoryAccountRepository;
import kata.bank.infra.repository.InMemoryTransactionRepository;
import kata.bank.usecase.UserTransactionUseCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.*;

public class UserTransactionUseCaseTest {

    public static final BigDecimal INITIAL_BALANCE = valueOf(100);
    public static final AccountId IBAN = AccountId.from("FR76998738939873973");

    UserTransactionUseCase userTransactionUseCase;
    InMemoryAccountRepository accountRepository;
    InMemoryTransactionRepository transactionRepository;

    @Before
    public void setUp(){
        accountRepository = new InMemoryAccountRepository();
        transactionRepository = new InMemoryTransactionRepository();
        userTransactionUseCase = new UserTransactionUseCase(transactionRepository, accountRepository, transactionRepository);

        Account account = new Account(IBAN, INITIAL_BALANCE);
        accountRepository.saveAccount(account);
    }

    @Test
    public void should_update_account_and_transaction_history_when_deposit(){
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = userTransactionUseCase.makeDeposit(IBAN.getIban(), valueOf(100), now);

        assertEquals(Transaction.Type.DEPOSIT, transaction.getType());
        assertTrue(transaction.getAmount().compareTo(valueOf(100)) == 0);
        assertTrue(transaction.getBalance().compareTo(valueOf(200)) == 0);
        assertEquals(now, transaction.getDate());
        assertEquals(IBAN, transaction.getAccountId());

        List<Transaction> transactions = transactionRepository.findAccountTransactions(IBAN);
        assertFalse(transactions.isEmpty());
        assertTrue(transactions.get(0).equals(transaction));

        Account account = accountRepository.findAccount(IBAN).get();
        assertTrue(account.getBalance().compareTo(valueOf(200)) == 0);
    }

    @Test
    public void should_update_account_and_transaction_history_when_withdrawal(){
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = userTransactionUseCase.makeWithdrawal(IBAN.getIban(), valueOf(100), now);

        assertEquals(Transaction.Type.WITHDRAWAL, transaction.getType());
        assertTrue(transaction.getAmount().compareTo(valueOf(100)) == 0);
        assertTrue(transaction.getBalance().compareTo(valueOf(0)) == 0);
        assertEquals(now, transaction.getDate());
        assertEquals(IBAN, transaction.getAccountId());

        List<Transaction> transactions = transactionRepository.findAccountTransactions(IBAN);
        assertFalse(transactions.isEmpty());
        assertTrue(transactions.get(0).equals(transaction));

        Account account = accountRepository.findAccount(IBAN).get();
        assertTrue(account.getBalance().compareTo(valueOf(0)) == 0);
    }
}
