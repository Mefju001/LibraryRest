package com.library.Service;


import com.library.Entity.Author;
import com.library.Repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Lazy
public class AuthorService {
    private final AuthorRepository authorRepository ;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findAuthorsByNameAndSurnameIs(String name, String surname)
    {
        return authorRepository.findAuthorsByNameAndSurnameIs(name,surname);
    }
    @Transactional
    public Author save(Author author)
    {
        return authorRepository.save(author);
    }
}
