package stringhelper;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;

/**
list of tests:
1 - If the string is null, then return null;
2 - If the string is empty, then return empty;
3 - if the length of string is equal to 1, then return itself;
4 - If the length of the string is equal to two, then swap the first character with the last one;
5 - If the length of the string is greater than two, then swap the last character with the next to last one.
**/

@Test
public class StringHelperTest {


    private StringHelper helper;

    @BeforeMethod
    private void setUp() {
        helper = new StringHelper();
    }

    public void givenNullWhenReverveThenReturnNull() {
        String res = helper.reverseLastTwoChars(null);
        assertNull(res);
    }

    public void givennEmptyStringWhenReverveThenReturnEmptyString() {
        String res = helper.reverseLastTwoChars("");
        assertEquals(res, "");
    }

    public void givenStringWithOneCharWhenReverseThenReturnSameString() {
        String res = helper.reverseLastTwoChars("A");
        assertEquals(res, "A");
    }

    public void givenStringWithTwoCharWhenReverseThenReturnReverseString() {
        String res = helper.reverseLastTwoChars("AB");
        assertEquals(res, "BA");
    }

    public void givenStringWithMoreThanTwoCharWhenReverseThenReturnReverseWithLastTwoCharsReverse() {
        String res = helper.reverseLastTwoChars("RANI");
        assertEquals(res, "RAIN");
    }


    @DataProvider
    private Object[][] computeValuesForReverse() {
        return new Object[][] {
            {null, null},
            {"", ""},
            {"A", "A"},
            {"AB", "BA"},
            {"RANI", "RAIN"}
        };
    }


    @Test(dataProvider = "computeValuesForReverse")

    public void givenStringWhenReverseThenReverseThenReverseLastTwoCharsOfString(String toReverse, String expectedResult) {

        String res = helper.reverseLastTwoChars(toReverse);
        assertEquals(res, expectedResult);
    }

    /**The teacher preferes the privious approach  */

}
