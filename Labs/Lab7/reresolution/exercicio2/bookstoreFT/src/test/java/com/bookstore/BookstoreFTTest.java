package com.bookstore;

import org.mockito.Mockito;
import org.mockito.InOrder;
import static org.mockito.Mockito.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

import com.bookstore.service.BookstoreServiceImpl;
import com.bookstore.service.BookstoreService;
import com.bookstore.domain.SearchCriteria;
import com.bookstore.domain.Book;
import com.bookstore.exception.CannotProcessRequestException;
import com.bookstore.exception.ServiceUnavailableException;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Test
public class BookstoreFTTest {

    //Arrange for all tests

    private BookstoreService mockBookstoreServicePrimary;

    private BookstoreService mockBookstoreServiceSecondary;

    private BookstoreFT mockBookstoreFT;

    @BeforeMethod private void setup() {
        mockBookstoreServicePrimary = mock(BookstoreService.class);
        mockBookstoreServiceSecondary = mock(BookstoreService.class);
        mockBookstoreFT = new BookstoreFT(mockBookstoreServicePrimary, mockBookstoreServiceSecondary);
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
        assertThrows(NullPointerException.class, () -> mockBookstoreFT.addBook(null));
        verify(mockBookstoreServicePrimary, never()).storeBook(any(Book.class));
        verify(mockBookstoreServiceSecondary, never()).storeBook(any(Book.class));

    }

    @Test
    public void givenValidBookAndAvailableBothServicesWhenAddBookThenStoreBook() throws ServiceUnavailableException, CannotProcessRequestException {
        //Act
        mockBookstoreFT.addBook(book1);

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).storeBook(book1);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).storeBook(book1);

    }

    @Test
    public void givenPrimaryServiceUnavailableSecondaryAvailableWhenAddBookThenStoreBook() throws ServiceUnavailableException, CannotProcessRequestException {
        //Act
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).storeBook(book1);
        mockBookstoreFT.addBook(book1);

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).storeBook(book1);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).storeBook(book1);

    }

    @Test 
    void givenPrimaryServiceAvailableSecondaryUnavailableWhenAddBookThenStoreBook() throws ServiceUnavailableException, CannotProcessRequestException {
        //Act
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServiceSecondary).storeBook(book1);
        mockBookstoreFT.addBook(book1);

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).storeBook(book1);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).storeBook(book1);

    }

    @Test
    void givenBothServicesUnvailableWhenAddBookThenThrowException() throws ServiceUnavailableException {
        //Act
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).storeBook(book1);
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServiceSecondary).storeBook(book1);

        //Assert
        assertThrows(CannotProcessRequestException.class, () -> mockBookstoreFT.addBook(book1));
        verify(mockBookstoreServicePrimary, atLeastOnce()).storeBook(book1);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).storeBook(book1);
    }

    //////////////////////////// search Test Cases ////////////////////////////


    //////Both Services Available       
    @Test
    public void givenAvailableBothServiceWhenPriceIsNullAndSeveralBooksSatisfyCriteriaThenReturnAllBooksWithinCriteria() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange 
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreServicePrimary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", null);

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, never()).findBooks(search);
        assertEquals(6, res.size());
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));
    }

    @Test
    public void givenAvailableBothServiceWhenPriceIsWithinTheCriteriaThenReturnBooksWithinCriteriaWithLowerPriceThanReferecedInFunction() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreServicePrimary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(25));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, never()).findBooks(search);
        assertEquals(4, res.size());
        assertEquals(res, List.of(book4, book6, book7, book8));

    }

    @Test
    public void givenAvailableBothServiceWhenPriceIsLowerThanAllBooksThatFitTheCriteriaReturnNull() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreServicePrimary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(5));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, never()).findBooks(search);
        assertNull(res);

    }

    @Test
    public void givenAvailableBothServiceWhenPriceIsHigherThanAllBooksThatFitTheCriteriaReturnAllBooks() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        when(mockBookstoreServicePrimary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(50));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, never()).findBooks(search);
        assertEquals(6, res.size());
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));

    }


    //////Primary Service Unavailable
    @Test
    public void givenPrimaryServiceUnavailableSecondaryServiceAvailableWhenPriceIsNullAndSeveralBooksSatisfyCriteriaThenReturnAllBooksWithinCriteria() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange 
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).findBooks(search);
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));
        

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", null);

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).findBooks(search);
        assertEquals(6, res.size());
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));
    }

    @Test
    public void givenPrimaryServiceUnavailableSecondaryServiceAvailableWhenPriceIsWithinTheCriteriaThenReturnBooksWithinCriteriaWithLowerPriceThanReferecedInFunction() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).findBooks(search);
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(25));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).findBooks(search);
        assertEquals(4, res.size());
        assertEquals(res, List.of(book4, book6, book7, book8));

    }

    @Test
    public void givenPrimaryServiceUnavailableSecondaryServiceAvailableWhenPriceIsLowerThanAllBooksThatFitTheCriteriaReturnNull() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).findBooks(search);
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(5));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).findBooks(search);
        assertNull(res);

    }

    @Test
    public void givenPrimaryServiceUnavailableSecondaryServiceAvailableWhenPriceIsHigherThanAllBooksThatFitTheCriteriaReturnAllBooks() throws ServiceUnavailableException, CannotProcessRequestException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).findBooks(search);
        when(mockBookstoreServiceSecondary.findBooks(search)).thenReturn(List.of(book4, book5, book6, book7, book8, book9));

        //Act
        List<Book> res = mockBookstoreFT.search("Author3", "Title3", new BigDecimal(50));

        //Assert
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).findBooks(search);
        assertEquals(6, res.size());
        assertEquals(res, List.of(book4, book5, book6, book7, book8, book9));

    }

    //Both services are unavailable

    @Test
    public  void givenBothServicesUnvailableWhenSearchThenThrowException() throws ServiceUnavailableException {
        //Arrange
        SearchCriteria search = new SearchCriteria("Title3", "Author3");
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServicePrimary).findBooks(search);
        doThrow(new ServiceUnavailableException()).when(mockBookstoreServiceSecondary).findBooks(search);

        //Assert
        assertThrows(CannotProcessRequestException.class, () -> mockBookstoreFT.search("Author3", "Title3", new BigDecimal(50)));
        verify(mockBookstoreServicePrimary, atLeastOnce()).findBooks(search);
        verify(mockBookstoreServiceSecondary, atLeastOnce()).findBooks(search);
    }

}
