package com.mert.zeybek.dto.Response;

import com.mert.zeybek.model.Book;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

public class BookResponse extends RepresentationModel<BookResponse> {
    private String Isbn;
    private int stockQuantity;
    private Date createdDate;
    private String author;
    private String title;
    private Date updatedDate;
    private double price;

    public BookResponse(Book book) {
        this.Isbn = book.getIsbn();
        this.stockQuantity = book.getStockQuantity();
        this.createdDate = book.getCreatedAt();
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.updatedDate = book.getUpdatedAt();
        this.price = book.getPrice();
    }

    public BookResponse(String isbn, String title, String author, double price, int stockQuantity, Date updatedAt) {
        this.Isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.updatedDate = updatedAt;
    }

    public BookResponse() {
    }

    public BookResponse(String isbn, String title, String author, double price, int stockQuantity, Date createdAt, Date updatedAt) {
        this.Isbn = isbn;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.createdDate = createdAt;
        this.updatedDate = updatedAt;
    }

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
