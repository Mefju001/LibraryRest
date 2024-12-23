package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Data
@Table(name = "favorite_user")
public class FavoriteUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int ID;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "books_id")
    private Book book;  // Klucz obcy odnoszący się do encji Book

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Klucz obcy odnoszący się do encji library
}
