package kata.bank.domain;

import kata.bank.domain.exception.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    public static final BigDecimal INITIAL_BALANCE = valueOf(100);
    public static final String IBAN = "FR76998738939873973";

    Account account;

    @Before
    public void setUp(){
        account = new Account(AccountId.from(IBAN), INITIAL_BALANCE);
    }

    @Test
    public void shouldUpdateBalanceAfterDeposit(){
        Account afterDeposit = account.deposit(valueOf(100));
        assertTrue(valueOf(200).compareTo(afterDeposit.getBalance()) == 0);
    }

    @Test
    public void shouldUpdateBalanceAfterWithdrawal(){
        Account afterWithdrawal = account.withdraw(valueOf(20));
        assertTrue(valueOf(80).compareTo(afterWithdrawal.getBalance()) == 0);
    }

    @Test(expected = InsufficientFundsException.class)
    public void shouldNotWithdrawWhenInsufficientFunds(){
        account.withdraw(valueOf(200));
    }

}
