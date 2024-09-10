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
*/

@Test
public class AccountTest {

  private Log log;

  @BeforeMethod   private void setUp() {
    log = mock(Log.class);
  }
 
  
  @Test
  public void whenCreatingAccountByDefaultThenAccountHasId0Balance0AndIsActive() {
    Account account;
    // Act
    account = new Account(log);

    // Assert
    assertEquals(account.getId(), 0);
    assertEquals(account.getBalance(), 0.0);
    assertTrue(account.isActive());
  }

  @Test
  public void givenAnIdAndBalanceWhenCreatingAccountThenAccountHasGivenIdAndBalanceAndIsActive() {
    Account account;
    int accountId = 20;
    double value = 100.0;

    // Act
    account = new Account(accountId, value, log);

    // Assert
    assertEquals(account.getId(), accountId);
    assertEquals(account.getBalance(), value);
    assertTrue(account.isActive());
  }
  
  @Test
  public void givenAnIdAndBalanceWhenCreatingAccountThenOperationIsLogged() {
    Account account;
    int accountId = 200;
    double value = 140.0;

    // Act
    account = new Account(accountId, value, log);

    // Assert
    verify(log).writeTo(accountId, OperationType.CREATION, value, true);
  }

  @Test
  public void whenCreatingAccountByDefaultThenOperationIsLogged() {
    Account account;
    int accountId = 0;
    double value = 0.0;

    // Act
    account = new Account(log);

    // Assert
    verify(log).writeTo(accountId, OperationType.CREATION, value, true);
  }
 
}
