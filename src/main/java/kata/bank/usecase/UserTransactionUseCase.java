package kata.bank.usecase;

import kata.bank.domain.*;
import kata.bank.domain.exception.AccountNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserTransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionIdGenerator transactionIdGenerator;

    public UserTransactionUseCase(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionIdGenerator transactionIdGenerator) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionIdGenerator = transactionIdGenerator;
    }

    public Transaction makeDeposit(String iban, BigDecimal amount, LocalDateTime date){
        return makeTransaction(Transaction.Type.DEPOSIT, iban, amount, date);
    }

    public Transaction makeWithdrawal(String iban, BigDecimal amount, LocalDateTime date){
        return makeTransaction(Transaction.Type.WITHDRAWAL, iban, amount, date);
    }

    private Transaction makeTransaction(Transaction.Type type, String iban, BigDecimal amount, LocalDateTime date){
        Account account = accountRepository.findAccount(AccountId.from(iban))
                .orElseThrow(() -> new AccountNotFoundException());
        Account accountAfterTransaction = doTransact(type, amount, account);
        Transaction transaction = Transaction.builder()
                .withReference(transactionIdGenerator.generate())
                .withType(type)
                .withAccountId(AccountId.from(iban))
                .withDate(date)
                .withAmount(amount)
                .withBalance(accountAfterTransaction.getBalance())
                .build();
        updateAccount(accountAfterTransaction);
        saveTransaction(transaction);

        return transaction;
    }

    private Account doTransact(Transaction.Type type, BigDecimal amount, Account account) {
        if(Transaction.Type.DEPOSIT.equals(type)) {
            return account.deposit(amount);
        }
        return account.withdraw(amount);
    }

    private void saveTransaction(Transaction transaction) {
        transactionRepository.addTransaction(transaction);
    }

    private void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

}
