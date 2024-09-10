package com.bookstore.domain;

import java.io.Serializable;

/**
 * This class represents a book´s category. Each existing category should be represented as an
 * intance of this classe.
 */

public class Category implements Serializable {

    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
