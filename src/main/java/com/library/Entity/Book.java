package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "books")
public class Book
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Book_id")
    private int ID;

    @Column(name = "title")
    private String title;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;  // Klucz obcy odnoszący się do encji Author

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;  // Klucz obcy odnoszący się do encji Genre

    @Column(name = "language")
    private String language;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;  // Klucz obcy odnoszący się do encji Publisher

    @Column(name = "pages")
    private int pages;

    @Column(name = "price")
    private float price;
}
