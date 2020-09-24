package model;

import java.util.Objects;

public class Book {
    private String title;
    private String isbn;
    private String publisher;
    private int year;
    private int authorId;
    private int id;

    public Book(String title, String isbn, String publisher, int year, int authorId) {
        this.title = title;
        this.isbn = isbn;
        this.publisher = publisher;
        this.year = year;
        this.authorId = authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", year=" + year +
                ", authorId=" + authorId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year &&
                Objects.equals(title, book.title) &&
                Objects.equals(isbn, book.isbn) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(authorId, book.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, isbn, publisher, year, authorId);
    }
}
