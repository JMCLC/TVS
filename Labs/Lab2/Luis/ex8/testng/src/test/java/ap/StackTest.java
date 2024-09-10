package ap;
 
import org.testng.Assert;
import org.testng.annotations.Test;

public class StackTest {

    @Test
    public void testPush() throws PilhaCheiaException {

        //Arrange
        Stack stack = new Stack(5);
        //Act
        stack.push(1);
        stack.push(2);
        //Assert
        Assert.assertEquals(stack.size(), 2);
    }

    @Test(expectedExceptions = PilhaCheiaException.class)
    public void testPushWhenStackIsFull() throws PilhaCheiaException {
        //Arrange
        Stack stack = new Stack(2);
        //Act
        stack.push(1);
        stack.push(2);
        stack.push(3); // Expects PilhaCheiaException
    }
}