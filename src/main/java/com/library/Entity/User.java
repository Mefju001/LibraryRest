package com.library.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;
    @Column(name = "enabled")
    private String enabled;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "age")
    private int age;

}
