package com.library.Controller;

import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Admin")
public class UserAndRoleController {
    private final BookService bookService;
    private final BookLibraryService bookLibraryService;

    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    @Autowired
    public UserAndRoleController(BookService bookService, BookLibraryService bookLibraryService, GenreService genreService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.bookLibraryService = bookLibraryService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }
}
