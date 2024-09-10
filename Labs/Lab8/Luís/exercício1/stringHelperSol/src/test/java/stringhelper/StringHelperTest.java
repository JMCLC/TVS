package stringhelper;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

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

  @BeforeMethod private void setUp() {
    helper = new StringHelper();
  }

  public void givenNullStringWhenReverseLastTwoCharsThenReturnNull() {
    String res = helper.reverseLastTwoChars(null);

    assertNull(res);
  }

  public void givenEmptyStringWhenReverseLastTwoCharsThenReturnEmpty() {
    String res = helper.reverseLastTwoChars("");

    assertEquals(res, "");
  }

  public void givenStringWithOneCharWhenReverseLastTwoCharsThenReturnSameString() {
    String res = helper.reverseLastTwoChars("A");

    assertEquals(res, "A");
  }

  public void givenStringWithTwoCharWhenReverseLastTwoCharsThenReturnReverseString() {
    String res = helper.reverseLastTwoChars("AB");

    assertEquals(res, "BA");
  }

  public void givenStringWithMoreThan2CharsWhenReverseLastTwoCharsThenReturnStringWithTheLAstTwoCharsReversed() {
    String res = helper.reverseLastTwoChars("RAIN");

    assertEquals(res, "RANI");
  }

  @DataProvider private Object[][] computeValuesForReverse() {
    return new Object[][] { {null, null}, {"", ""}, {"A", "A"}, {"AB", "BA"}, {"RAIN", "RANI"}};
  }

  @Test(dataProvider="computeValuesForReverse")
  public void givenStringWhenReverseThenReturnStringWithLastTwoCharsChanged(String toReverse, String expectedRes) {
    String res = helper.reverseLastTwoChars(toReverse);

    assertEquals(res, expectedRes);
  }
}
