package exam;

import exam.exception.InvalidOperationException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class TestQuestion {

    @DataProvider
    private Object[][] computeValidValuesForConstructor() {
        return new Object[][] {
            {"abcdef", Arrays.asList("A","B","C","D"), 2, Arrays.asList("abcdwe","avctbag"), 2},
            {"abcde", Arrays.asList("A","B","C","D"), 0, Arrays.asList("cmutbagh","abcdswe","abcdefgh"), 7},
            {"ef", Arrays.asList("A","B"), 1, Arrays.asList("abcdef"), 7},
            {"apsij", Arrays.asList("A","B","C","D"), 2, Arrays.asList("abcdwe","avctbag"), 9}
        };
    }


    @Test(dataProvider = "computeValidValuesForConstructor")
    public void testConstructorWithValidValues(String body, List<String> choices, int correctChoice, List<String> topics, int weight) throws InvalidOperationException {
        //Act
        Question question = new Question(body, choices, correctChoice, topics, weight);

        //Assert
        assertEquals(question.getChoices(), choices);
        assertEquals(question.getTopics(), topics);
        assertEquals(question.getWeight(), weight);
    }

    @DataProvider
    private Object[][] computeInvalidDataForConstructor() {
        return new Object[][] {
            {"abcd", Arrays.asList("A", "B", "C","D","E","F","G","H","I"), 5, Arrays.asList("abcdef", "abcdefg"), 6},
            {"bc", Arrays.asList("A", "B", "C","D","E"), 5, Arrays.asList("fsdfdfd","asdsdfdf","sdfdsf","svjutimae","dasdfsdfw"), 9},
            {"efg", Arrays.asList("A", "B", "C","D","E","F"), 3, Arrays.asList("abcde"), 8},
            {"asaspk", Arrays.asList("A", "B", "C","D","E"), 3, Arrays.asList("abcdef", "abcdef"),10}
        };
    }

    @Test(dataProvider = "computeInvalidDataForConstructor")
    public void testConstructorWithInvalidData(String body, List<String> choices, int correctChoice, List<String> topics, int weight) {
        // Act & Assert
        assertThrows(InvalidOperationException.class, () -> {
            new Question(body, choices, correctChoice, topics, weight);
        });
    }
}
    