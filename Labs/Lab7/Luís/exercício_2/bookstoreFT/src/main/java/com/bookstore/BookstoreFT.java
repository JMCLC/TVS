package com.bookstore;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.bookstore.domain.Book;
import com.bookstore.domain.SearchCriteria;
import com.bookstore.service.BookstoreService;
import com.bookstore.service.BookstoreServiceImpl;
import com.bookstore.exception.ServiceUnavailableException;
import com.bookstore.exception.CannotProcessRequestException;

public class BookstoreFT {

    private BookstoreService bookstoreServicePrimary;
    private BookstoreService bookstoreServiceSecondary;

    public BookstoreFT(BookstoreService primary, BookstoreService secondary) {
        this.bookstoreServicePrimary = primary;
        this.bookstoreServiceSecondary = secondary;
    }

    /**
     * Find all books in store that obey to the search criteria (author and title) and price. If the price is not set, then 
     * it just returns the books found by BookstoreService. If pricce argument is set, then it filters
     * the books found by BookstoreService whose price are higher than price.
     * If there are no books that verify the author, title and price criterias, then it should return
     * a null list.
     *
     * If the bookstoreservice is not available, then it throws the CannotProcessRequestException.
     **/
    List<Book> search(String author, String title, BigDecimal price) throws CannotProcessRequestException {
        SearchCriteria criteria = new SearchCriteria(title, author);

        List<Book> books;

        try {
            books = bookstoreServicePrimary.findBooks(criteria);
        } catch (com.bookstore.exception.ServiceUnavailableException e) {
            try {
                books = bookstoreServiceSecondary.findBooks(criteria);
            } catch (com.bookstore.exception.ServiceUnavailableException ee) {
                throw new CannotProcessRequestException(ee);
            }
        }

        if (price != null) {
            List<Book> booksWithPrice = new ArrayList<Book>();

            for (Book b : books) {
                if (b.getPrice().compareTo(price) <= 0)
                    booksWithPrice.add(b);
            }

            books = booksWithPrice;
        }

        if (books.isEmpty())
            return null;

        return books;
    }

    /**
     * Adds a new book to the persistent repository. 
     *
     * @param b the book to add. It should not be null. If it is null, then the bookstoreservice
     * is not invoked and a NullPointerException is thrown.
     *
     * @exception NullPointerException if b is null
     * @exception CannotProcessRequestException if the BookStoreService is not available.
     **/
    public void addBook(Book b) throws CannotProcessRequestException {
        if (b == null)
            throw new NullPointerException("the book argument cannot be null");
        
        boolean added = true;

        try {
            bookstoreServicePrimary.storeBook(b);
        } catch (com.bookstore.exception.ServiceUnavailableException e) {
            added = false;
        }

        try {
            bookstoreServiceSecondary.storeBook(b);
        } catch (com.bookstore.exception.ServiceUnavailableException e) {
            if (!added)
                throw new CannotProcessRequestException(e);
        }


    }
}
