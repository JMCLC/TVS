package bank;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

/**
As a user, I want to be able to create a bank account. A bank account should have an identifier (integer), the status of the account (active/inactive) and a balance:
 1 -  By default, a bank account has the identifier 0, is active and has a balance equal to 0.0.
 2 -  It should also be possible to create active bank accounts with a given identifier and balance.
 3 - Check that criation of an account writes on the log.
 4- Check that criation of a default account writes on the log.
 5- Check that an active account can be freezed
*/

@Test
public class AccountTest {

    private AccountTest accountTest;

    private Log log;


    @BeforeMethod
    private void setUp() {
        log = Mockito.mock(Log.class);
    }

    public void whenCreatingDefaultAccountThenAccountIsActiveAndHasId0AndBalance0() {
        Account a = new Account(log);
        assertEquals(a.getId(), 0);
        assertEquals(a.isActive(), true);
        assertEquals(a.getBalance(), 0.0);
    }

    public void givenIdAndBalanceWhenCreatingAccountThenAccountIsActiveAndHasGivenIdAndBalance() {
        Account a = new Account(1, 100.0, log);
        assertEquals(a.getId(), 1);
        assertEquals(a.isActive(), true);
        assertEquals(a.getBalance(), 100.0);
    }

    public void givenIdAndBalanceWhenCreatingAccountThenOperationIsLogged() {
        Account a = new Account(20, 40.0, log);
        verify(log).writeTo(20, OperationType.CREATION, 40.0, true);
    }

    public void givenCreatingDefaultAccountThenOperationIsLogged() {
        Account a = new Account(log);
        verify(log).writeTo(0, OperationType.CREATION, 0.0, true);
    }


    public void givenActiveAccountWhenFreezeAccountThenAccountIsInactive() {
        Account a = new Account(20, 40.0, log);
        a.freeze();
        assertEquals(a.isActive(), false);
        verify(log).writeTo(20, OperationType.FREEZE, 0.0, false);
    }


}
