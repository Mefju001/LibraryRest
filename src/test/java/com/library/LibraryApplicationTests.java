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


}
