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

}
