package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;
import com.bookstore.domain.Cart;
import com.bookstore.domain.Category;
import com.bookstore.domain.SearchCriteria;
import com.bookstore.exception.ServiceUnavailableException;

/**
 * Main entry point for the bookstore.
 */
public interface BookstoreService {

    /**
     * Find the book with the given isbn.
     * 
     * @param isbn the isbn to search
     *
     * @throws ServiceUnavailableException if the repository is not available
     *
     * @return the book with the given isbn.
     */
    Book findBookByIsbn(String isbn) throws ServiceUnavailableException;

    /**
     * Search for {@link Book}s that meet the given {@link BookSearchCriteria}.
     * 
     * @param bookSearchCriteria the search criteria.
     *
     * @throws ServiceUnavailableException if the repository is not available
     *
     * @return list of books (never <code>null</code>).
     */
    List<Book> findBooks(SearchCriteria bookSearchCriteria) throws ServiceUnavailableException;

    /**
     * Find all the categories available.
     * 
     * @throws ServiceUnavailableException if the repository is not available
     *
     * @return list with all the categories
     */
    List<Category> findAllCategories() throws ServiceUnavailableException;

    /**
     * Store a book in the repository.
     * 
     * @throws ServiceUnavailableException if the repository is not available
     *
     * @param book the book to store. It cannot be null.
     */
    void storeBook(Book book) throws ServiceUnavailableException;
}
