package com.duyhq.firebasedemo;

public class Book {
    public String key;

    public String name;
    public String author;
    public int isbn;

    public Book() {
        this.name = "";
        this.author = "";
        this.isbn = -1;
    }

    public Book(String name, String author, int isbn) {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
    }
}
