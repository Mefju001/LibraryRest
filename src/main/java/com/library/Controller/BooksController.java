package com.library.Controller;

import com.library.Entity.Book;
import com.library.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController(BookService bookService)
    {
        this.bookService = bookService;
    }

    @GetMapping("/ListOfBooks")
    public List<Book> ListOfBooks()
    {
        return bookService.ListOfBooks();
    }

    @PostMapping("/AddBook")
    public void AddBook(@RequestBody Book book)
    {
        bookService.save(book);
    }

    @PutMapping("/UpdateBook")
    public Book UpdateBook(@RequestBody Book book)
    {
        return bookService.save(book);
    }
    @DeleteMapping("/Delete/{id}")
    public void DeleteBook(@PathVariable int id)
    {
        bookService.Delete(id);
    }

}
