package com.library.Repository;


import com.library.Entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre,Integer> {
    Genre findGenresByGenreNameIs(String genreName);
}
