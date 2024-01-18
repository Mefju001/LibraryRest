package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int ID;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;  // Klucz obcy odnoszący się do encji Author

    @Column(name = "publication_year")
    private int publication_year;

    @Column(name = "isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;  // Klucz obcy odnoszący się do encji Genre

    @Column(name = "language")
    private String language;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;  // Klucz obcy odnoszący się do encji Publisher


    @Column(name = "pages")
    private int pages;

    @Column(name = "price")
    private float price;
}
