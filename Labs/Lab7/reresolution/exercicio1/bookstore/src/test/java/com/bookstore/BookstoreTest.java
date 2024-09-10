package com.bookstore;

import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;

import com.bookstore.service.BookstoreService;
import com.bookstore.service.BookstoreServiceImpl;
import com.bookstore.domain.SearchCriteria;
import com.bookstore.domain.Book;
import com.bookstore.exception.CannotProcessRequestException;
import com.bookstore.exception.ServiceUnavailableException;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Test
public class BookstoreTest {

    //Arrange for all tests 

    private BookstoreService mockBookstoreService;

    private Bookstore mockBookstore;

    @BeforeMethod private void setup() {
        mockBookstoreService = mock(BookstoreService.class);
        mockBookstore = new Bookstore(mockBookstoreService);
    }
    
    private static final Book book1 = new Book("Title1", new BigDecimal(10), "Author1", "isbn1");

    private static final Book book2 = new Book("Title2", new BigDecimal(20), "Author2", "isbn2");

    private static final Book book3 = new Book("Title3", new BigDecimal(30), "Author3", "isbn3");


    // books to test the search method
    private static final Book book4 = new Book("Title3", new BigDecimal(25), "Author3", "isbn4");

    private static final Book book5 = new Book("Title3", new BigDecimal(35), "Author3", "isbn5");

    private static final Book book6 = new Book("Title3", new BigDecimal(10), "Author3", "isbn6");

    private static final Book book7 = new Book("Title3", new BigDecimal(15), "Author3", "isbn7");

    private static final Book book8 = new Book("Title3", new BigDecimal(23), "Author3", "isbn8");

    private static final Book book9 = new Book("Title3", new BigDecimal(40), "Author3", "isbn9");


    //////////////////////////// addBook Test Cases ////////////////////////////


    @Test
    public void givenNullBookWhenAddBookThenDoNothing() throws ServiceUnavailableException, CannotProcessRequestException, NullPointerException {
        //Act & Assert
        assertThrows(NullPointerException.class, () -> mockBookstore.addBook(null));
        verify(mockBookstoreService, never()).storeBook(any(Book.class));
    }

    @Test 
    public void givenValidBookAndAvailableServiceWhenAddBookThenStoreBook() throws ServiceUnavailableException, CannotProcessRequestException{
        //Act 
        mockBookstore.addBook(book1);

        //Assert
        verify(mockBookstoreService, atLeastOnce()).storeBook(book1);
    }


    @Test
    public void givenUnavailableServiceWhenAddBookThenThrowCannotProcess() throws ServiceUnavailableException {
        //Act
        doThrow(new ServiceUnavailableException()).when(mockBookstoreService).storeBook(book1);
        //Assert
        assertThrows(CannotProcessRequestException.class, () -> mockBookstore.addBook(book1));
        verify(mockBookstoreService, atLeastOnce()).storeBook(book1);
    }

    //////////////////////////// findBooks Test Cases ////////////////////////////

    @Test
    public void givenAvailableServiceWhenPriceIsNullAndSeveralBooksSatisfyCriteriaThenReturnAllBooksWithinCriteria() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreService.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstore.search("Author3", "Title3", null);

        //Assert
        verify(mockBookstoreService, atLeastOnce()).findBooks(search);
        assertEquals(6, res.size());
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));

    }

    @Test
    public void givenAvailableServiceWhenPriceIsWithinTheCriteriaThenReturnBooksWithinCriteriaWithLowerPriceThanReferecedInFunction() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreService.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstore.search("Author3", "Title3", new BigDecimal(25));

        //Assert
        verify(mockBookstoreService, atLeastOnce()).findBooks(search);
        assertEquals(4, res.size());
        assertEquals(res, List.of(book4, book6, book7, book8));

    }

    @Test
    public void givenAvailableServiceWhenPriceIsLowerThanAllBooksThatFitTheCriteriaReturnNull() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreService.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstore.search("Author3", "Title3", new BigDecimal(5));

        //Assert
        verify(mockBookstoreService, atLeastOnce()).findBooks(search);
        assertNull(res);
    }

    @Test
    public void givenAvailableServiceWhenPriceIsHigherThanAllBooksThatFitTheCriteriaReturnAllBooks() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreService.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstore.search("Author3", "Title3", new BigDecimal(50));

        //Assert
        verify(mockBookstoreService, atLeastOnce()).findBooks(search);
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));
    }

    @Test
    public void givenUnavailableServiceWhenSearchThenThrowCannotProcess() throws ServiceUnavailableException {
        //Arrange
        //doThrow(new ServiceUnavailableException()).when(mockBookstoreService).findBooks(any(SearchCriteria.class));
        //Theres no need for this since the function findBooks is not void
        when(mockBookstoreService.findBooks(any(SearchCriteria.class))).thenThrow(new ServiceUnavailableException());

        //Act & Assert
        assertThrows(CannotProcessRequestException.class, () -> mockBookstore.search("Author3", "Title3", new BigDecimal(50)));
        verify(mockBookstoreService, atLeastOnce()).findBooks(any(SearchCriteria.class));
    }

    @Test
    public void givenAvailableServiceWhenServiceFindNoBooksThenReturnNull() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title8", "Author8");
        when(mockBookstoreService.findBooks(search)).thenReturn(List.of());

        //Act
        List<Book> res = mockBookstore.search("Author8", "Title8", null);

        //Assert
        verify(mockBookstoreService, atLeastOnce()).findBooks(search);
        assertNull(res);
    }



    

}
