/**
 * This class represents a calculator that is able to sum and divide two Integer numbers.
 * Each calculator has a name and keeps the number of operations executed without errors.
 **/

 package ap;

 public class MultipleCalculator {

    public int[] compute(int x, int y) throws InvalidNumberException {
        // Check if x and y are positive
        if (x <= 0 || y <= 0) {
            throw new InvalidNumberException("Both x and y must be positive numbers.");
        }
        
        // Check if y is greater than x
        if (y <= x) {
            throw new InvalidNumberException("y must be greater than x.");
        }

        // Calculate the number of multiples of x between 1 and y
        int count = 0;
        for (int i = 1; i <= y; i++) {
            if (i % x == 0) {
                count++;
            }
        }

        // If no multiples of x are found, return an empty array
        if (count == 0) {
            return new int[0];
        }

        // Create an array to store the multiples of x
        int[] multiples = new int[count];
        int index = 0;

        // Store the multiples of x in the array
        for (int i = 1; i <= y; i++) {
            if (i % x == 0) {
                multiples[index++] = i;
            }
        }

        return multiples;
    }
}

class InvalidNumberException extends Exception {
    public InvalidNumberException(String message) {
        super(message);
    }
}