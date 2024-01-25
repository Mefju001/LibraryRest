package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Library")
public class Library {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "library_id")
    private int ID;
    @Column(name = "library_name")
    private String nazwa_Biblioteki;
    @Column(name = "library_address")
    private String adres_Biblioteki;

}
