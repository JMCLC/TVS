@Test
public class TestBookstore {
    private BookStoreService mockService;
    private BookStore store;

    @BeforeMethod private void setup() {
        mockService = mock(BookStoreService.class);
        store = new BookStore(mockService);
    }

    private static final Book book = new Book("Author", "Title",new BigDecimal(10), "isbn 1");

    private static final Book book2 = new Book("Author2", "Title2",new BigDecimal(20), "isbn 2");

    private static final Book book3 = new Book("Author3", "Title3",new BigDecimal(30), "isbn 3");



    @Test
    Public void givenNullBookWhenAddBookThenDoNothing() throws ServiceUnavailableException, CannotProcessRequestException, NullPointerException {
        //Act & Assert
        store.addBook(null);
        //Assert
        assertThrows(NullPointerException.class, () -> store.addBook(null));
        verify(mockService, never()).addBook(any(Book.class));

    }



    //Used when the return is not void
    //when(mo.invocation) thenReturn then throw
    //doReturn(_).when(mock).invocation
    //doThrow


    @Test
    public void givenUnavailableServiceWhenAddBookThenThrowCannotProcess() throws ServiceUnavailableException {
        doThrow(new ServiceUnavailableException.class).when(mockService).storeBook(book);
        //Act & Assert
        assertThrows(CannotProcessRequestException.class, () -> store.addBook(book));
        //if you dont specify the once() it is subintended that it is once
        verify(mockService, once()).storeBook(book);
    }


    @Test
    public void givenValidBookAndAvailableServiceWhenAddBookThenStoreBook() throws ServiceUnavailableException, CannotProcessRequestException {
    //Act
    store.addBook(book);
    //Assert
    verify(mockService, once()).storeBook(book);


    }

    @Test
    public void givenAvailableServiceWhenPriceIsNullAndSeveralBooksSatisfyCriteriaThenReturnBooks() throws ServiceUnavailableException, CannotProcessRequestException {
        

        SearchCriteria search = new SearchCriteria("Title", "author");
        when(mockService.findBooks(search).thenReturn(List.of(book, book2, book3)));

        //Act
        var res = store.search("author", "Title", null);

        //Assert
        assertEquals(res, List.of(book, book2, book3));

    }
}











