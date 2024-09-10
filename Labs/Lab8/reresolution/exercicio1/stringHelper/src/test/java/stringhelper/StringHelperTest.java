package stringhelper;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.*;


import java.beans.Transient;

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


    //ARRANGE

    StringHelper stringHelper;

    @BeforeMethod
    public void setUp() {
        stringHelper = new StringHelper();
    }

    @Test
    public void testReverseLastTwoCharsNull() {
        //ACT
        String res = stringHelper.reverseLastTwoChars(null);
        //ASSERT
        assertNull(res);
    }

    @Test
    public void testReverseLastTwoCharsEmpty() {
        //ACT
        String res = stringHelper.reverseLastTwoChars("");
        //ASSERT
        assertEquals("", res);
    }

    @Test 
    public void testReverseLastTwoCharsOneChar() {
        //ACT
        String res = stringHelper.reverseLastTwoChars("A");
        //ASSERT
        assertEquals("A", res);
    }

    @Test
    public void testReverseLastTwoCharsTwoChars() {
        //ACT
        String res = stringHelper.reverseLastTwoChars("AB");
        //ASSERT
        assertEquals("BA", res);
    }

    @Test
    public void testReverseLastTwoCharsMoreThanTwoChars() {
        //ACT
        String res = stringHelper.reverseLastTwoChars("RAIN");
        //ASSERT
        assertEquals("RANI", res);
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
    public void testReverseLastTwoChars(String toReverse, String expectedResult) {
        //ACT
        String res = stringHelper.reverseLastTwoChars(toReverse);
        //ASSERT
        assertEquals(expectedResult, res);
    }
}
