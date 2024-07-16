package com.library.Controller;

import com.library.Entity.Book;
import com.library.Entity.BookLibrary;
import com.library.Entity.Library;
import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/All")
    public List<Library> ListOflibrary()
    {
        return bookLibraryService.ListOfLibrary();
    }
    @PostMapping("/AddLibrary")
    public Library AddLibrary(@RequestBody Library library)
    {
        return bookLibraryService.AddLibrary(library);
    }
    @PutMapping("/UpdateLibrary")
    public Library UpdateLibrary(@RequestBody Library library)
    {
        return bookLibraryService.AddLibrary(library);
    }
    @DeleteMapping("/{id}")
    public void DeleteLibrary(@PathVariable int id) {
        if(bookLibraryService.ListOfBooksInLibraryByID(id).isEmpty())
            bookLibraryService.DeleteLibrary(id);
        else
            bookLibraryService.DeleteLibraryAndBook(id);
            //throw new RuntimeException("Jest przypisany");
    }
    @PostMapping("/AddBookLibrary")
    public BookLibrary AddBookLibrary(@RequestBody BookLibrary BookLibrary) {

        return bookLibraryService.AddBookToLibrary(BookLibrary);
    }
}
