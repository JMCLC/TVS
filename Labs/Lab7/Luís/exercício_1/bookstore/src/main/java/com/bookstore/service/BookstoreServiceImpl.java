package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;
import com.bookstore.domain.SearchCriteria;
import com.bookstore.domain.Cart;
import com.bookstore.domain.Category;
import com.bookstore.exception.ServiceUnavailableException;

//import com.bookstore.repository.BookRepository;
//import com.bookstore.repository.CategoryRepository;

/**
 * @see BookstoreService
 * 
 */

public class BookstoreServiceImpl implements BookstoreService {

    // private BookRepository bookRepository;

    // private CategoryRepository categoryRepository;

    @Override
    public List<Book> findBooks(SearchCriteria bookSearchCriteria) throws ServiceUnavailableException {
        // return this.bookRepository.findBooks(bookSearchCriteria);
        return null; // just for compilation.
    }

    @Override
    public Book findBookByIsbn(String isbn) throws ServiceUnavailableException {
        // return this.bookRepository.findByIsbn(id);
        return null; // just for compilation.
    }

    @Override
    public List<Category> findAllCategories() throws ServiceUnavailableException {
        // return this.categoryRepository.findAll();
        return null; // just for compilation.
    }

    @Override
    public void storeBook(Book book) throws ServiceUnavailableException {
        // this.bookRepository.storeBook(book);
    }
}
