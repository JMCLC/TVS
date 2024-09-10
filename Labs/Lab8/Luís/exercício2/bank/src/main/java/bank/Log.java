package bank;

public interface Log {
  void writeTo(int accountId, OperationType type, double value, boolean sucess);
}
