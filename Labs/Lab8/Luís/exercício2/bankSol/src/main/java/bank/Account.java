package bank;

public class Account {
  
  private int accountId;
  private double balance;

  public Account(Log log) {
    this(0, 0.0, log);
  }

  public Account(int accountId, double balance, Log log) {
    this.accountId = accountId;
    this.balance = balance;

    log.writeTo(accountId, OperationType.CREATION, balance, true);
  }

  public int getId() {
    return accountId;
  }
  
  public double getBalance() {
    return balance;
  }

  public boolean isActive() {
    return true;
  }
}
