package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Data
@Table(name = "userandrole")
public class UserAndRole {
    @Id
    @Column(name = "id")
    private String id;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "username")
    private User user;  // Klucz obcy odnoszący się do encji User

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "authority")
    private Authority authority;  // Klucz obcy odnoszący się do encji Authority
}