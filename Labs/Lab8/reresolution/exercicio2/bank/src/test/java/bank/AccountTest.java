package bank;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;

import bank.exception.IlegalOperationException;

import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;


import java.beans.Transient;

/**
As a user, I want to be able to create a bank account. A bank account should have an identifier (integer), the status of the account (active/inactive) and a balance:
 1 -  By default, a bank account has the identifier 0, is active and has a balance equal to 0.0.
 2 -  It should also be possible to create active bank accounts with a given identifier and balance.
 3 - Check that criation of an account writes on the log.
*/

@Test
public class AccountTest {

    Log log;

    @BeforeMethod
    private void setUp() {
        log = Mockito.mock(Log.class);
    }


    @Test
    public void createAccountDefault() {
        //ARRANGE
        Account account = new Account(log);

        //ASSERT
        assertEquals(0, account.getIdentifier());
        assertEquals("ACTIVE", account.getStatus());
        assertEquals(0.0, account.getBalance());
    }

    @Test
    public void createAccountWithIdAndBalance() {
        //ARRANGE
        Account account = new Account(30, 245.70, log);

        //ASSERT
        assertEquals(30, account.getIdentifier());
        assertEquals("ACTIVE", account.getStatus());
        assertEquals(245.70, account.getBalance());
    }


    //Arrange
    Account account;

    @BeforeMethod
    private void setUp2() {
        account = new Account(30, 245.70, log);
    }


    @Test 
    public void checkLogCreatedWhenAccountWithIdAndBalance() {
        //ASSERT
        verify(log).writeTo(30, OperationType.CREATION, 245.70, true);
    }

    @Test
    public void freezeActiveAccount() {

        //ACT
        account.freezeAccount();

        //ASSERT
        assertEquals("INACTIVE", account.getStatus());
    }

    @Test
    public void freezeInactiveAccountThrowIllegalStateException() {
        //ARRANGE
        account.freezeAccount();
        //ACT & ASSERT
        assertThrows(IllegalStateException.class, () -> account.freezeAccount());
    }

    @Test
    public void checkLogWhenSuccessfullyFreezeAccount() {
        //ACT
        account.freezeAccount();

        //ASSERT
        verify(log).writeTo(30, OperationType.FREEZE, 245.70, true);
    }

    @Test
    public void checkLogWhenUnsuccessfullyFreezeAccount() {
        //ARRANGE
        account.freezeAccount();

        //ACT & ASSERT
        assertThrows(IllegalStateException.class, () -> account.freezeAccount());
        verify(log).writeTo(30, OperationType.FREEZE, 245.70, false);
    }

    @Test
    public void unfreezeInactiveAccount() {
        //Arrange
        account.freezeAccount();

        //Act
        account.unfreezeAccount();

        //Assert
        assertEquals("ACTIVE", account.getStatus());
    }

    @Test
    public void unfreezeActiveAccountThrowIllegalStateException() {
        //ACT & ASSERT
        assertThrows(IllegalStateException.class, () -> account.unfreezeAccount());
    }

    @Test
    public void checkLogWhenSuccessfullAccountUnfreeze() {
        //ARRANGE
        account.freezeAccount();

        //ACT
        account.unfreezeAccount();

        //ASSERT
        verify(log).writeTo(30, OperationType.ACTIVATE, 245.70, true);
    }

    @Test
    public void checkLogWhenUnsuccessfullAccountUnfreeze() {
        //ACT & ASSERT
        assertThrows(IllegalStateException.class, () -> account.unfreezeAccount());
        verify(log).writeTo(30, OperationType.ACTIVATE, 245.70, false);
    }


    @Test
    public void addBalanceToAccountActiveAccount() throws IlegalOperationException {
        //ACT
        account.addBalance(100.0);

        //ASSERT
        assertEquals(345.70, account.getBalance());
    }

    @Test
    public void addBalanceToInactiveAccountThrowIlegalOperationException() {
        //ARRANGE
        account.freezeAccount();

        //ACT & ASSERT
        assertThrows(IlegalOperationException.class, () -> account.addBalance(100.0));
    }

    @Test
    public void addNegativeBalanceToAccountThrowIlegalOperationException() {
        //ACT & ASSERT
        assertThrows(IlegalOperationException.class, () -> account.addBalance(-100.0));
    }


    @Test
    public void checkLogWhenSuccessfullAddBalance() throws IlegalOperationException {
        //ACT
        account.addBalance(100.0);

        //ASSERT
        verify(log).writeTo(30, OperationType.ADD, 100.0, true);
    }

    @Test
    public void checkLogWhenUnsuccessfullAddBalance() throws IlegalOperationException{
      
       //ASSERT
       assertThrows(IlegalOperationException.class, () -> account.addBalance(-100.0));
       verify(log).writeTo(30, OperationType.ADD, -100.0, false);
    }

    @Test
    public void withdrawBalanceFromAccountActiveAccount() throws IlegalOperationException {
        //ACT
        account.withdrawBalance(100.0);

        //ASSERT
        assertEquals(145.70, account.getBalance());
    }

    @Test
    public void withdrawBalanceFromInactiveAccountThrowIlegalOperationException() {
        //ARRANGE
        account.freezeAccount();

        //ACT & ASSERT
        assertThrows(IlegalOperationException.class, () -> account.withdrawBalance(100.0));
    
    }

    @Test
    public void withdrawNegativeBalanceFromAccountThrowIlegalOperationException() {
        //ACT & ASSERT
        assertThrows(IlegalOperationException.class, () -> account.withdrawBalance(-100.0));
    }

    @Test
    public void withdrawBalanceGreaterThanAccountBalanceThrowIlegalOperationException() {
        //ACT & ASSERT
        assertThrows(IlegalOperationException.class, () -> account.withdrawBalance(300.0));
    }

    @Test
    public void checkLogWhenSuccessfullWithdrawBalance() throws IlegalOperationException {
        //ACT
        account.withdrawBalance(100.0);

        //ASSERT
        verify(log).writeTo(30, OperationType.WITHDRAWAL, 100.0, true);
    }

    @Test
    public void checkLogWhenUnsuccessfullWithdrawBalance() throws IlegalOperationException{
        //ASSERT
        assertThrows(IlegalOperationException.class, () -> account.withdrawBalance(300.0));
        verify(log).writeTo(30, OperationType.WITHDRAWAL, 300.0, false);
    }



    



}
