package ap;
 
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test 
public class TestCalculator {


    //NomeValids -> "ab", "abcde", "abc"

    //invalid -> "a", "abcdef", null

    //No need to put @Test annotation here since it is implicit in the method name
    public void testConstructorWithValidNameWith2Chars() {
        //Arrange
        String name = "ab";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    public void testConstructorWithValidNameWith3Chars() {
        //Arrange
        String name = "abc";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }

    //No need to make the test for 4 charactes since it is already covered by the test for 3 characters
    //We only need to text in the barier values and one in the middle

    public void testConstructorWithValidNameWith5Chars() {
        //Arrange
        String name = "abcde";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);
        assertEquals(calculator.getNumberOfOperations(), 0);
    }


    //Arrange
    private Calculator calc;

    @DataProvider
    private Object[][] computeValidValuesForConstructor() {
        return new Object[][] {
            {"ab"},
            {"abcde"},
            {"abc"}, 
        };
    }

    @Test(dataProvider = "computeValidValuesForConstructor")
    public void testConstructorWithValidName(String name) {
        //Arrange
        Calculator calc;
        //Act
        calc = new Calculator(name);
        //Assert
        assertEquals(calc.getName(), name);
        assertEquals(calc.getNumberOfOperations(), 0);
    }

    @DataProvider
    private Object[][] computeInvalideDataForConstructor() {
        return new Object[][] {
            {"a"},
            {"abcdef"},
            {null}};
    }

    //Various alternatives to test invalid name in the calculator constructor

    @Test (dataProvider = "computeInvalideDataForConstructor")
    public void testConstructorWithInvalidNameAlt1(String name) {
        try {
            //Act
            new Calculator(name);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //Assert
            assertEquals(e.getMessage(), "no name");
        }
    }

    //This is a better then the next one since it is more explicit and we use asserts to check the exception
    @Test (dataProvider = "computeInvalideDataForConstructor")
    public void testConstructorWithNameInvalidSizeAlt2(String name) {
        //Act
        assertThrows(IllegalArgumentException.class, () -> {new Calculator(name);});
    }


    //In very specific cases we can use the expectedExceptions attribute of the @Test annotation
    @Test (expectedExceptions = IllegalArgumentException.class, dataProvider = "computeInvalideDataForConstructor")
    public void testConstructorWithNameInvalidSizeAlt3(String name) {
        //Act
        new Calculator(name);
        // If we put asserts here they will not be executed
    }

    @BeforeMethod private void setup() {
        //Arrage for all the tests above
        calc = new Calculator("test");
    }


    // {param1, param2, expectedResult} for example
    @DataProvider
    private Object[][] computeDataForSum() {
        return new Object[][] {
            {3, 4, 7},
            {-3 , -44, -47},
            {null, 4, 4},
            {-3, null, -3},
            {null, null, 0},
            {0, 0, 0},
            {null, 5, 5},
            {-3, null, -3},
            {5, 3, 8},
            {-5, 3, -2},
            {-8, 3, -5},
            {2, -6, -4}};
    }

    @Test(dataProvider = "computeDataForSum")
    public void testSum(Integer param1, Integer param2, Integer expectedResult) {
        //Act
        Integer result = calc.sum(param1, param2);

        //Assert
        assertEquals(result, expectedResult);
        assertEquals(calc.getNumberOfOperations(), 1);
    }

    @DataProvider
    private Object[][] computeInvalidDataForDivide() {
        return new Object[][] {
            {1, null},
            {-2, 0},
            {null, null},
            {0, 0},
            {0, null}};
    }

    @Test(dataProvider = "computeInvalidDataForDivide")
    public void testDivide(Integer param1, Integer param2) {

        //Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {calc.divide(param1, param2);});
        assertEquals(calc.getNumberOfOperations(), 0);
    }

    @DataProvider
    private Object[][] computeValidDataForDivide() {
        return new Object[][] {
            {0, 4, 0},
            {null, 50, 0},
            {10, 5, 2},
            {-100, -5, 20},
            {-7, 4, -1},
            {null, 2, 0},
            {4, 2, 2},
            {-6, -3, 2},
            {-6, 2, -3},
            {7, -3, -2}};    
    }

    @Test(dataProvider = "computeValidDataForDivide")
    public void testDivide(Integer dividendo, Integer divider, Integer expectedResult) {
        //Act
        Integer result = calc.divide(dividendo, divider);

        //Assert
        assertEquals(result, expectedResult);
        assertEquals(calc.getNumberOfOperations(), 1);
    }
}