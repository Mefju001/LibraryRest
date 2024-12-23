package com.library.Controller;

import com.library.Entity.Book;
import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Books")
public class BooksController {
    private final BookService bookService;
    private final Library library;
    @Autowired
    public BooksController(BookService bookService, Library library)
    {
        this.bookService = bookService;
        this.library = library;
    }
    @GetMapping("/")
    public ResponseEntity<List<Book>> ListOfBooks(@RequestParam(required = false) String nameAndsurname,
                                                  @RequestParam(required = false) String Searchname,
                                                  @RequestParam(required = false,name = "price1") Float price1,
                                                  @RequestParam(required = false,name = "price2") Float price2,
                                                  @RequestParam(required = false,name = "year1") Integer Year1,
                                                  @RequestParam(required = false,name = "year2") Integer Year2,
                                                  @RequestParam(required = false) String sortBy,
                                                  @RequestParam(required = false) String order)
    {
        if (nameAndsurname != null) {
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
        } else if (sortBy!=null&&order!=null) {
            return bookService.ListBooksBySort(sortBy,order);
        } else {
            return bookService.ListOfBooks();
        }
    }
    @GetMapping("/id")
    public ResponseEntity<Book> ListOfBooks(@RequestParam Integer id)
    {
        return bookService.FindByID(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Book> AddBook(@RequestBody Book book) {
        return bookService.save(book);
    }
    @PutMapping("/")
    public ResponseEntity<Book> UpdateBook(@RequestBody Book book)
    {
        return bookService.save(book);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteBook(@PathVariable(name = "id") Integer id)
    {
        return bookService.Delete(id);
    }

}
