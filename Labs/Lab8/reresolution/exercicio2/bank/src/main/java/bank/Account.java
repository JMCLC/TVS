package bank;

import bank.exception.IlegalOperationException;

public class Account {

    private int identifier;

    private String status;

    private double balance;

    private Log log;

    String ACTIVE = "ACTIVE";

    String INACTIVE = "INACTIVE";


    public Account(Log log) {
        this.identifier = 0;
        this.status = ACTIVE;
        this.balance = 0.0;
        this.log = log;
    }

    public Account(int identifier, double balance, Log log) {
        this.identifier = identifier;
        this.status = ACTIVE;
        this.balance = balance;
        this.log = log;
        log.writeTo(identifier, OperationType.CREATION, balance, true);
    }

    public int getIdentifier() {
        return this.identifier;
    }

    public String getStatus() {
        return this.status;
    }

    public double getBalance() {
        return this.balance;
    }

    public void freezeAccount() {
        if (this.status.equals(ACTIVE)){
            this.status = INACTIVE;
            this.log.writeTo(this.identifier, OperationType.FREEZE, this.balance, true);
        } else {
            this.log.writeTo(this.identifier, OperationType.FREEZE, this.balance, false);
            throw new IllegalStateException();
        }
    }

    public void unfreezeAccount() {

        if (this.status.equals(INACTIVE)){
            this.status = ACTIVE;
            this.log.writeTo(this.identifier, OperationType.ACTIVATE, this.balance, true);
        } else {
            this.log.writeTo(this.identifier, OperationType.ACTIVATE, this.balance, false);
            throw new IllegalStateException();
        }
    }


    public void addBalance(double value) throws IlegalOperationException{
        if (this.status.equals(ACTIVE) && value > 0.0) {
            this.balance += value;
            this.log.writeTo(this.identifier, OperationType.ADD, value, true);
        }
        else {
            this.log.writeTo(this.identifier, OperationType.ADD, value, false);
            throw new IlegalOperationException();
        }
        
    }


    public void withdrawBalance(double value) throws IlegalOperationException{
        if (this.status.equals(ACTIVE) && value > 0.0 && value <= this.balance) {
            this.balance -= value;
            this.log.writeTo(this.identifier, OperationType.WITHDRAWAL, value, true);
        }
        else {
            this.log.writeTo(this.identifier, OperationType.WITHDRAWAL, value, false);
            throw new IlegalOperationException();
            
        }
    }
}
