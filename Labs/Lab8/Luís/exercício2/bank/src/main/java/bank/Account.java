package bank;

public class Account {

    private int id;

    private double balance;

    private boolean isActive;

    private Log log;

    public Account(Log log) {
        this(0, 0.0, log);
    }


    public Account( int id, double balance, Log log) {
        this.id = id;
        this.balance = balance;
        this.isActive = true;
        this.log = log;
        log.writeTo(id, OperationType.CREATION, balance, true);
        
    }

    public double getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getId() {
        return id; 
    }

    public void freeze() {
        if(isActive()) {
            this.isActive = false;
            this.balance = 0.0;
            this.log.writeTo(id, OperationType.FREEZE, 0.0, false);
        }
    }


}
