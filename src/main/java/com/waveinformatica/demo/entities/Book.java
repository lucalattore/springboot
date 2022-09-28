package com.waveinformatica.demo.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books", indexes = {
    @Index(columnList = "isbn", unique = true)
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @ManyToOne
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private final List<Reading> readings;

    public Book() {
        this.readings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Reading> getReadings() {
        return readings;
    }
}
