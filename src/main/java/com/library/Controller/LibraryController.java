package com.library.Controller;

import com.library.Entity.BookLibrary;
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
    private final Library library;
    @Autowired
    public LibraryController(BookService bookService, Library library)
    {
        this.bookService = bookService;
        this.library = library;
    }

    @GetMapping("/")
    public ResponseEntity<List<com.library.Entity.Library>> ListOflibrary()
    {
        return library.ListOfLibrary();
    }
    @PostMapping("/")
    public ResponseEntity<com.library.Entity.Library> AddLibrary(@RequestBody com.library.Entity.Library library)
    {
        return this.library.AddLibrary(library);
    }
    @PutMapping("/")
    public ResponseEntity<com.library.Entity.Library> UpdateLibrary(@RequestBody com.library.Entity.Library library)
    {
        return this.library.AddLibrary(library);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteLibrary(@PathVariable int id) {
       return library.DeleteLibrary(id);
    }
    @PostMapping("/AddBookLibrary")
    public ResponseEntity<BookLibrary> AddBookLibrary(@RequestBody BookLibrary BookLibrary) {
        return library.AddBookToLibrary(BookLibrary);
    }
    @GetMapping("/BookLibrary")
    public ResponseEntity<List<BookLibrary>> Listbookinlibrary()
    {
        return library.ListOfBooksInLibrary();
    }
}
