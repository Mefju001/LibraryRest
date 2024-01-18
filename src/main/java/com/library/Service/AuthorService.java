package com.library.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorService authorService;

    @Autowired
    public AuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
}
