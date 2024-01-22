package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Publisher_id")
    private int publisherId;

    @Column(name = "publisher_name")
    private String publisherName;
}
