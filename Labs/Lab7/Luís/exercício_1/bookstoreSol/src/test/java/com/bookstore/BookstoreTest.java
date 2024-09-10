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

  private static final Book book = new Book("one", new BigDecimal(10), "author", "isbn1010");
  private static final Book bookTwo = new Book("two", new BigDecimal(20), "author", "isbn1020");
  private static final Book bookThree = new Book("three", new BigDecimal(30), "author", "isbn1030");
  
  private BookstoreService mockService = mock(BookstoreService.class);
  private Bookstore store = new Bookstore(mockService);

  @BeforeMethod private void setup() {
    mockService = mock(BookstoreService.class);
    store = new Bookstore(mockService);
  }
  
  public void givenValidBookWhenAddBookThenBookIsAddedToRepository() throws CannotProcessRequestException, ServiceUnavailableException {
    // Given a valid book and an available service

    // When we add a book
    store.addBook(book);

    // Then the book is added to the service
    verify(mockService).storeBook(book);
  }

   public void givenValidBookAndUnavailableServiceWhenAddBookThenThrowsCAnnotProcessException() throws ServiceUnavailableException {
    // Given a valid book and an unavailable service
    doThrow(ServiceUnavailableException.class).when(mockService).storeBook(book);
    // When we add a book
    assertThrows(CannotProcessRequestException.class, () -> store.addBook(book));

    // Then service is invoked
    verify(mockService).storeBook(book);
  }

  public void givenNullBookAndAvailableServiceWhenAddBookThenServiceIsNotCalled() throws CannotProcessRequestException, ServiceUnavailableException {
    // Given a null book and an available service
    Book book = null;

    // When we add a book
    assertThrows(NullPointerException.class, () -> store.addBook(book));

    // Then service is invoked
    verify(mockService, never()).storeBook(any());
  }

  public void givenServiceIsUnavailableWhenSearchThenThrowException() throws ServiceUnavailableException {
    // Given a valid search criteria and an unavailable service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    // no need to use doThrow(ServiceUnavailableException.class).when(mockService).findBooks(criteria))
    // since findBooks is not void
    when(mockService.findBooks(criteria)).thenThrow(ServiceUnavailableException.class);
    // When we search, throw CannotProcessRequest
    assertThrows(CannotProcessRequestException.class, () -> store.search("author", null, null));
  }

  public void givenServiceAvailableWhenServiceFindNoBooksThenReturnNull()
    throws ServiceUnavailableException, CannotProcessRequestException {
    // Given a valid search criteria and an available service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    // no need to program mock since the default behavior is what we want
    // when(mockService.findBooks(criteria)).thenReturn(List.of());

    // When we search, throw CannotProcessRequest
    assertNull(store.search("author", null, null));
    // Then just check we interacted with the service using the correct interface
    verify(mockService).findBooks(criteria);
  }

  public void givenServiceAvailableAndAllBooksReturnedByServiceSatisfyCriteriaWithoutPriceWhenServiceFinBooksThenSameBooks()
    throws ServiceUnavailableException, CannotProcessRequestException {
    // Given a valid search criteria and an unavailable service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    when(mockService.findBooks(criteria)).thenReturn(List.of(book, bookTwo, bookThree));
    
    // When we search and all books found by service satisfy all criteria
    List<Book> res = store.search("author", null, null);
    //

    assertEquals(res.size(), 3);
    assertEquals(res, List.of(book, bookTwo, bookThree));
  }

  public void givenServiceAvailableAndAllBookSatisfyPriceCretiriaWhenSearchThenReturnAllBooks()
    throws ServiceUnavailableException, CannotProcessRequestException {
    // Given a valid search criteria and an unavailable service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    when(mockService.findBooks(criteria)).thenReturn(List.of(book, bookTwo, bookThree));
    
    // When we search and all books found by service satisfy all criteria
    List<Book> res = store.search("author", null, new BigDecimal(35));
    //
    assertEquals(res.size(), 3);
    assertEquals(res, List.of(book, bookTwo, bookThree));
  }

  public void givenServiceAvailableAndSomeNotSatisfyPriceCretiriaWhenSearchThenReturnSubSet()
    throws ServiceUnavailableException, CannotProcessRequestException {
    // Given a valid search criteria and an unavailable service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    when(mockService.findBooks(criteria)).thenReturn(List.of(book, bookTwo, bookThree));
    
    // When we search and all books found by service satisfy all criteria
    List<Book> res = store.search("author", null, new BigDecimal(25));
    //
    assertEquals(res.size(), 2);
    assertEquals(res, List.of(book, bookTwo));
  }

  public void givenServiceAvailableAndNoBookSatisfyPriceCretiriaWhenServiceFinBooksThenReturnNull()
    throws ServiceUnavailableException, CannotProcessRequestException {
    // Given a valid search criteria and an unavailable service
    SearchCriteria criteria = new SearchCriteria(null, "author");
    when(mockService.findBooks(criteria)).thenReturn(List.of(book, bookTwo, bookThree));
    
    // When we search and all books found by service satisfy all criteria
    List<Book> res = store.search("author", null, new BigDecimal(5));
    //
    assertNull(res);
  }

}
