package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Genre_id")
    private int genreId;

    @Column(name = "genre_name")
    private String genreName;
}
