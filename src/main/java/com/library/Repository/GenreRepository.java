package com.library.Repository;


import com.library.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
}
