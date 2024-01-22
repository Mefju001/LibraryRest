package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Author_id")
    private int authorId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;


}
