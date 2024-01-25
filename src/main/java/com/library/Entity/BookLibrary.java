package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "booklibrary")
public class BookLibrary {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;  // Klucz obcy odnoszący się do encji Book

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "library_id")
    private Library library;  // Klucz obcy odnoszący się do encji library
}
