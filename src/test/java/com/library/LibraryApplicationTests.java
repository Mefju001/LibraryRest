package com.library;

import com.library.Entity.Author;
import com.library.Entity.Book;
import com.library.Repository.AuthorRepository;
import com.library.Service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryApplicationTests {
    @Autowired
    BookService bookService;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void findbyname()
    {
        Author authors = authorRepository.findAuthorsByNameAndSurnameIs("Stephen","King");
        System.out.println(authors.toString());
        assertNotNull(authors);
    }
    @Test
    void findbynamebookService()
    {
        //List<Book> books = bookService.ListBooksOfAuthorAndSurname("George R.R","Martin");
        //System.out.println(books.toString());
        //assertFalse(books.isEmpty());
    }

    @Test
    void findbypricebookService()
    {
        List<Book> books = bookService.ListBooksForPrice(20F, 40F);
        System.out.println(books.size());
        System.out.println(books.toString());
        assertFalse(books.isEmpty());
    }

}
