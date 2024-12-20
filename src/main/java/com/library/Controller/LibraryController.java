package com.library.Controller;

import com.library.Entity.Book;
import com.library.Entity.BookLibrary;
import com.library.Entity.Library;
import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Library")
public class LibraryController {
    private final BookService bookService;
    private final BookLibraryService bookLibraryService;

    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    @Autowired
    public LibraryController(BookService bookService, BookLibraryService bookLibraryService, GenreService genreService, AuthorService authorService, PublisherService publisherService)
    {
        this.bookService = bookService;
        this.bookLibraryService = bookLibraryService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Library>> ListOflibrary()
    {
        return bookLibraryService.ListOfLibrary();
    }
    @PostMapping("/")
    public ResponseEntity<Library> AddLibrary(@RequestBody Library library)
    {
        return bookLibraryService.AddLibrary(library);
    }
    @PutMapping("/")
    public ResponseEntity<Library> UpdateLibrary(@RequestBody Library library)
    {
        return bookLibraryService.AddLibrary(library);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteLibrary(@PathVariable int id) {
       return bookLibraryService.DeleteLibrary(id);
    }
    @PostMapping("/AddBookLibrary")
    public ResponseEntity<BookLibrary> AddBookLibrary(@RequestBody BookLibrary BookLibrary) {

        return bookLibraryService.AddBookToLibrary(BookLibrary);
    }
}
