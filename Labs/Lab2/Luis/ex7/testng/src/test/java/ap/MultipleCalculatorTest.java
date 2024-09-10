package ap;
 
import org.testng.Assert;
import org.testng.annotations.Test;

public class MultipleCalculatorTest {

    @Test
    public void testComputeWithValidInput() throws InvalidNumberException {
        MultipleCalculator calculator = new MultipleCalculator();
        int[] expected = {3, 6, 9};
        int[] result = calculator.compute(3, 10);
        Assert.assertEquals(result, expected);
    }

    @Test(expectedExceptions = InvalidNumberException.class)
    public void testComputeWithInvalidX() throws InvalidNumberException {
        MultipleCalculator calculator = new MultipleCalculator();
        calculator.compute(-3, 10);
    }

    @Test(expectedExceptions = InvalidNumberException.class)
    public void testComputeWithInvalidY() throws InvalidNumberException {
        MultipleCalculator calculator = new MultipleCalculator();
        calculator.compute(3, 2);
    }

    @Test
    public void testComputeWithNoMultiples() throws InvalidNumberException {
        MultipleCalculator calculator = new MultipleCalculator();
        int[] expected = {4};
        int[] result = calculator.compute(4, 5);
        Assert.assertEquals(result, expected);
    }
}