/**
 * This class represents a calculator that is able to sum and divide two Integer numbers.
 * Each calculator has a name and keeps the number of operations executed without errors.
 **/

 package ap;

 public class Stack {
    private Object[] elements;
    private int top;
    private int capacity;

    public Stack(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.top = -1;
    }

    public void push(Object elemento) throws PilhaCheiaException {
        if (top == capacity - 1) {
            throw new PilhaCheiaException("Stack is full.");
        }
        elements[++top] = elemento;
    }

    public Object pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elements[top--];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return top == -1;
    }
}

class PilhaCheiaException extends Exception {
    public PilhaCheiaException(String message) {
        super(message);
    }
}

class EmptyStackException extends Exception {
    public EmptyStackException() {
        super("Stack is empty.");
    }
}