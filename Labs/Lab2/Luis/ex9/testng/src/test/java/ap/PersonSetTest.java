package ap;
 
import org.testng.Assert;
import org.testng.annotations.Test;

public class PersonSetTest {

    @Test
    public void testAddPerson() throws InvalidInvocationException {
        //Arrange
        PersonSet personSet = new PersonSet(2);
        Person person1 = new Person("John", "123 Main St");
        Person person2 = new Person("Jane", "456 Elm St");
        //Act
        Assert.assertTrue(personSet.add(person1));
        Assert.assertTrue(personSet.add(person2));
        //Assert
        Assert.assertEquals(personSet.getSize(), 2);
    }

    @Test(expectedExceptions = InvalidInvocationException.class)
    public void testAddPersonToFullSet() throws InvalidInvocationException {

        //Arrange
        PersonSet personSet = new PersonSet(1);
        Person person1 = new Person("John", "123 Main St");
        //Act
        personSet.add(person1);
        personSet.add(person1); // Expects InvalidInvocationException
    }

    @Test
    public void testAddExistingPerson() throws InvalidInvocationException {
        //Arrange
        PersonSet personSet = new PersonSet(2);
        Person person1 = new Person("John", "123 Main St");
        //Assert
        Assert.assertTrue(personSet.add(person1));
        Assert.assertFalse(personSet.add(person1)); // Adding the same person again
        Assert.assertEquals(personSet.getSize(), 1);
    }
}