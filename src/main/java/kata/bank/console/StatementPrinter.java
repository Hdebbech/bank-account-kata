package kata.bank.console;

import kata.bank.domain.Statement;
import kata.bank.domain.Transaction;
import kata.bank.usecase.StatementUseCase;

public class StatementPrinter {

    private final StatementUseCase statementUseCase;

    public StatementPrinter(StatementUseCase statementUseCase) {
        this.statementUseCase = statementUseCase;
    }

    public void printStatement(String iban) {

        Statement statement = statementUseCase.makeAccountStatement(iban);
        System.out.println("Account : " + statement.getAccount().getId().getIban());
        System.out.println("Balance : " + statement.getAccount().getBalance());
        System.out.format("%16s%16s%16s%16s%16s", "date", "ref", "withdrawal", "deposit", "balance");
        System.out.println();
        statement.getTransactionsOrderedByEarlierDate()
                .forEach((transaction) -> {
                    if(Transaction.Type.DEPOSIT.equals(transaction.getType())){
                        System.out.format("%16s%16s%16s%16s%16s",
                                transaction.getDate().toLocalDate(),
                                transaction.getReference(),
                                "",
                                transaction.getAmount(),
                                transaction.getBalance());
                    }

                   else{
                        System.out.format("%16s%16s%16s%16s%16s",
                                transaction.getDate().toLocalDate(),
                                transaction.getReference(),
                                transaction.getAmount(),
                                "",
                                transaction.getBalance());
                    }

                   System.out.println();
                });

    }

    public static void main(String... args) {
        System.out.format("%16s%16s%16s%16s%16s", "date", "ref", "withdrawal", "deposit", "balance");
        System.out.println();
        System.out.format("%16s%16s%16s%16s%16s", "22-09-2021", "29393949", "200", "", "100");
    }
}
