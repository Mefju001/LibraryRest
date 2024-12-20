package com.library.Controller;

import com.library.Entity.Book;
import com.library.Entity.BookLibrary;
import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Books")
public class BooksController {
    private final BookService bookService;
    private final BookLibraryService bookLibraryService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    @Autowired
    public BooksController(BookService bookService, BookLibraryService bookLibraryService, GenreService genreService, AuthorService authorService, PublisherService publisherService)
    {
        this.bookService = bookService;
        this.bookLibraryService = bookLibraryService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping("")
    public List<Book> ListOfBooks(@RequestParam(required = false) Integer id,
                                  @RequestParam(required = false) String nameAndsurname,
                                  @RequestParam(required = false) String Searchname,
                                  @RequestParam(required = false,name = "price1") Float price1,
                                  @RequestParam(required = false,name = "price2") Float price2,
                                  @RequestParam(required = false,name = "year1") Integer Year1,
                                  @RequestParam(required = false,name = "year2") Integer Year2)
    {
        if(id!=null) {
            return bookService.FindByID(id);
        }
        else if (nameAndsurname != null) {
                return bookService.ListBooksOfAuthorAndSurname(nameAndsurname);
        }
        else if (Searchname!=null) {
            return bookService.ListbooksOfSearchName(Searchname);
        }
        else if (price1 != null && price2 !=null) {
            return bookService.ListBooksForPrice(price1, price2);
        }
        else if (Year1 != null && Year2 !=null) {
            return bookService.ListBooksForYear(Year1, Year2);
        }
        else {
            return bookService.ListOfBooks();
        }

    }
    @GetMapping("/Library")
    public List<BookLibrary> ListOfBooksInLibrary(@RequestParam(required = false) Integer id)
    {
        if(id==null) {
            return bookLibraryService.ListOfBooksInLibrary();
        }
        else{
            return bookLibraryService.ListOfBooksInLibraryByID(id);
        }
    }
    @GetMapping("/Sortowanie")
    public List<Book>Sortbook(@RequestParam(required = false) String sortBy,
                              @RequestParam(required = false) String order)
    {
        return bookService.ListBooksBySort(sortBy,order);
    }


    @PostMapping("")
    public void AddBook(@RequestBody Book book) {
        bookService.save(book);
    }
    @PutMapping("")
    public Book UpdateBook(@RequestBody Book book)
    {
        return bookService.save(book);
    }
    @DeleteMapping("/{id}")
    public void DeleteBook(@PathVariable int id)
    {
        bookService.Delete(id);
    }

}
