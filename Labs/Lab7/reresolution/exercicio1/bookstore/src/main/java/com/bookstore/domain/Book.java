package com.bookstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class represents a book.
 * 
 * 
 *
 */
public class Book implements Serializable {

    private String title;
    private BigDecimal price;
    private String author;
    private String isbn;
    private Category category;

    public Book(String title, BigDecimal price, String author, String isbn) {
        this.title = title;
        this.price = price;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Book other = (Book) object;

        return isbn.equals(other.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
