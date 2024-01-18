package com.library.Service;

import com.library.Entity.Book;
import com.library.Repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Transactional
    public List<Book>ListOfBooks()
    {
        return bookRepository.findAll();
    }
    @Transactional
    public Optional<Book> FindByID(int id)
    {
        return bookRepository.findById(id);
    }
    @Transactional
    public Book save(Book book)
    {
        return bookRepository.save(book);

    }
    @Transactional
    public void Delete(int id)
    {
         bookRepository.deleteById(id);
    }

}
