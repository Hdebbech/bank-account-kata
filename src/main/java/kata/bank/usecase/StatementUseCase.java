package kata.bank.usecase;

import kata.bank.domain.*;
import kata.bank.domain.exception.AccountNotFoundException;

import java.util.List;

public class StatementUseCase {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public StatementUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public Statement makeAccountStatement(String iban){
        AccountId id = AccountId.from(iban);
        Account account =  accountRepository.findAccount(id)
                .orElseThrow(() -> new AccountNotFoundException());
        List<Transaction> transactions = transactionRepository.findAccountTransactions(id);
        return new Statement(account, transactions);
    }
}
