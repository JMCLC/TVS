package ap;
 
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test 
public class TestCalculator {

    //implement test for the constructor


    //Basic tests without parameterized tests for valid inputs
    @Test
    public void testCustructorWith2Chars() {
        //Arrange
        String name = "ab";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);

    }

    @Test
    public void testCustructorWith3Chars() {
        //Arrange
        String name = "abc";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);

    }

    @Test
    public void testCustructorWith4Chars() {
        //Arrange
        String name = "abcd";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);

    }

    @Test
    public void testCustructorWith5Chars() {
        //Arrange
        String name = "abcde";
        //Act
        Calculator calculator = new Calculator(name);
        //Assert
        assertEquals(calculator.getName(), name);

    }


    //Parameterized tests for valid inputs

    @DataProvider
    private Object[][] computeValidValuesForConstructor() {
        return new Object[][] {
            {"ab"},
            {"abc"},
            {"abcd"},
            {"abcde"}
        };
    }

    @Test(dataProvider = "computeValidValuesForConstructor")
    public void testConstructorWithValidName(String name) {
        //Act
        Calculator calc = new Calculator(name);
        //Assert	
        assertEquals(calc.getName(), name);
    }

    //Non parameterized tests for invalid inputs

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithNullName() {
        //Arrange
        String name = null;
        //Act
        Calculator calc = new Calculator(name);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWith1Char() {
        //Arrange
        String name = "a";
        //Act
        Calculator calc = new Calculator(name);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWith6Chars() {
        //Arrange
        String name = "abcdef";
        //Act
        Calculator calc = new Calculator(name);
    }

    //Parameterized tests for invalid inputs

    @DataProvider
    private Object[][] computeInvalidDataForConstructor() {
        return new Object[][] {
            {null},
            {"a"},
            {"abcdef"}
        };
    }

    
    //This is a better then the next one since it is more explicit and we use asserts to check the exception
    @Test(dataProvider = "computeInvalidDataForConstructor")
    public void testConstructorWithNameInvalidSizeAlt2(String name) {
        //Act
        assertThrows(IllegalArgumentException.class, () -> {new Calculator(name);});
    }


     //In very specific cases we can use the expectedExceptions attribute of the @Test annotation
    @Test(dataProvider = "computeInvalidDataForConstructor", expectedExceptions = IllegalArgumentException.class)
    public void testConstructorWithInvalidNameAlt1(String name) {
        //Act
        new Calculator(name);
    }


    //implement test for the sum method


    // Arrange implementation before the tests themselves to avoid code duplication

    private Calculator calc;
    
    @BeforeMethod private void setup() {
        //Arrage for all the tests above
        calc = new Calculator("test");
    }


    //implement test for the sum method already with parameterized tests
    @DataProvider
    private Object[][] computeValidValuesForSum() {
        return new Object[][] {
            {1, 2, 3},
            {0, 0, 0},
            {0, 1, 1},
            {1, 0, 1},
            {1, -1, 0},
            {-1, 1, 0},
            {-1, -1, -2},
            {null, 1, 1},
            {1, null, 1},
            {null, null, 0}
        };
    }

    @Test(dataProvider = "computeValidValuesForSum")
    public void testSum(Integer a, Integer b, Integer expected) {
        //Act
        Integer result = calc.sum(a, b);
        //Assert
        assertEquals(result, expected);
    }

    //implement test for the divide method with parameterized tests
    @DataProvider
    private Object[][] computeValidValuesForDivide() {
        return new Object[][] {
            {1, 1, 1},
            {0, 1, 0},
            {1, -1, -1},
            {-1, 1, -1},
            {-1, -1, 1},
            {8,2,4},
            {2,8,0},
            {null, 1, 0},
        };
    }

    @Test(dataProvider = "computeValidValuesForDivide")
    public void testDivide(Integer a, Integer b, Integer expected) {
        //Act
        Integer result = calc.divide(a, b);
        //Assert
        assertEquals(result, expected);
    }

    @DataProvider
    private Object[][] computeInvalidValuesForDivide() {
        return new Object[][] {
            {1, 0},
            {0, 0},
            {null, 0},
            {null, null}
        };
    }

    @Test(dataProvider = "computeInvalidValuesForDivide", expectedExceptions = IllegalArgumentException.class)
    public void testDivideByZero(Integer a, Integer b) {
        //Act
        calc.divide(a, b);
    }

    @Test(dataProvider = "computeInvalidValuesForDivide")
    public void testDivideByZeroAlt(Integer a, Integer b) {
        //Act
        assertThrows(IllegalArgumentException.class, () -> {calc.divide(a, b);});
    }

    
}

