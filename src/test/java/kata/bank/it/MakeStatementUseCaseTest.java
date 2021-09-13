package kata.bank.it;

import kata.bank.console.StatementPrinter;
import kata.bank.domain.Account;
import kata.bank.domain.AccountId;
import kata.bank.infra.repository.InMemoryAccountRepository;
import kata.bank.infra.repository.InMemoryTransactionRepository;
import kata.bank.usecase.StatementUseCase;
import kata.bank.usecase.UserTransactionUseCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.math.BigDecimal.valueOf;

public class MakeStatementUseCaseTest {

    public static final BigDecimal INITIAL_BALANCE = valueOf(100);
    public static final AccountId IBAN = AccountId.from("FR76998738939873973");

    StatementUseCase statementUseCase;
    UserTransactionUseCase userTransactionUseCase;
    StatementPrinter statementPrinter;

    InMemoryAccountRepository accountRepository;
    InMemoryTransactionRepository transactionRepository;

    @Before
    public void setUp(){
        accountRepository = new InMemoryAccountRepository();
        transactionRepository = new InMemoryTransactionRepository();
        userTransactionUseCase = new UserTransactionUseCase(transactionRepository, accountRepository, transactionRepository);
        statementUseCase = new StatementUseCase(transactionRepository, accountRepository);
        statementPrinter = new StatementPrinter(statementUseCase);
        Account account = new Account(IBAN, INITIAL_BALANCE);
        accountRepository.saveAccount(account);
    }

    @Test
    public void print_statement(){
        userTransactionUseCase.makeDeposit(IBAN.getIban(), valueOf(1000), LocalDateTime.of(2021, 1, 20, 15, 20, 11));
        userTransactionUseCase.makeWithdrawal(IBAN.getIban(), valueOf(700), LocalDateTime.of(2021, 1, 25, 16, 20, 11));
        userTransactionUseCase.makeDeposit(IBAN.getIban(), valueOf(1000), LocalDateTime.of(2021, 2, 20, 15, 20, 11));
        userTransactionUseCase.makeDeposit(IBAN.getIban(), valueOf(1000), LocalDateTime.of(2021, 3, 20, 15, 20, 11));

        statementPrinter.printStatement(IBAN.getIban());
    }
}
