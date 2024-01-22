package com.library.Service;

import com.library.Entity.Author;
import com.library.Entity.Genre;
import com.library.Repository.BookRepository;
import com.library.Repository.GenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre findGenresByGenreNameIs(String genreName)
    {
        return genreRepository.findGenresByGenreNameIs(genreName);
    }
    @Transactional
    public Genre save(Genre genre)
    {
        return genreRepository.save(genre);
    }
}
