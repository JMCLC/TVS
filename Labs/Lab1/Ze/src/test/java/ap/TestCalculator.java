package ap;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class TestCalculator {
    
    public void testConstructorWithValidNameWith2Chars() {
        String name = "ab";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    public void testConstructorWithValidNameWith3Chars() {
        String name = "abc";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    public void testConstructorWithValidNameWith5Chars() {
        String name = "abcde";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    @DataProvider
    private Object[][] computeValidValuesForConstructor() {
        return new Object[][] {
            {"ab"},
            {"abc"},
            {"abcde"},
        };
    }

    @Test(dataProvider = "computeValidValuesForConstructor")
    public void testConstructorWithValidName(String name) {
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    // Normally this is the best way to test exceptions
    public void testConstructorWithNullThenThrowException() {
        try {
            Calculator calculator = new Calculator(null);
            fail("An exception should have been thrown");
        } catch (IllegalArgumentException e) {
        }
    }    

    // This one works to reduce code but in most cases you might need to make assertions after the exception is thrown
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithNullThenThrowExceptionV2() {
        Calculator calculator = new Calculator(null);
    }    
    
    @DataProvider
    private Object[][] computeInvalidValuesForConstructor() {
        return new Object[][] {
            {null},
            {"a"},
            {"abcdef"},
        };
    }

    @Test(dataProvider = "computeInvalidValuesForConstructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithInvalidName(String name) {
        Calculator calculator = new Calculator(name);
    }

    @DataProvider
    private Object[][] computeValuesForSum() {
        return new Object[][] {
            {4, 3, 7},
            {-3, -5, -8},
            {-3, 5, 2},
            {2, -10, -8},
            {null, 5, 5},
            {-3, null, -3},
            {null, null, 0},
        };
    }

    @Test(dataProvider = "computeValuesForSum")
    public void testSum(Integer a, Integer b, Integer expected) {
        //Arrange
        Calculator calculator = new Calculator("test");
        //Act
        Integer result = calculator.sum(a, b);
        //Assert
        assertEquals(result, expected);
        assertEquals(calculator.getNumberOfOperations(), 1);
    }

    @DataProvider
    private Object[][] computeInvalidValuesForDivide() {
        return new Object[][] {
            {5, 0},
            {5, null},
            {0, 0},
            {null, 0},
            {null, null},
        };
    }

    @Test(dataProvider = "computeInvalidValuesForDivide")
    public void testDivide(Integer a, Integer b) {
        //Arrange
        Calculator calculator = new Calculator("test");
        //Act
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(a, b));
        //Assert
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    private Calculator calc;

    @BeforeMethod
    private void setup() {
        calc = new Calculator("test");
    }

    @DataProvider
    private Object[][] computeValuesForDivide() {
        return new Object[][] {
            {6, 2, 3},
            {-3, 1, -3},
            {2, -2, -1},
            {-4, -2, 2},
            {5, 0, null},
            {5, null, null},
            {0, 0, null},
            {null, 0, null},
            {null, null, null},
        };
    }

    // Different way from the teacher's solution but allows to test valid and invalid in one test
    @Test(dataProvider = "computeValuesForDivide")
    public void testDivideV2(Integer a, Integer b, Integer expected) {
        try {
            //Arrange
            Calculator calculator = new Calculator("test");
            //Act
            Integer result = calculator.divide(a, b);
            //Assert
            assertEquals(result, expected);
            assertEquals(calculator.getNumberOfOperations(), 1);
        } catch (IllegalArgumentException e) {
            if (expected != null) {
                fail("An exception should not have been thrown");
            }
        }
    }
}
