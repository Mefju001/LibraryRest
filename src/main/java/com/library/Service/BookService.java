package com.library.Service;

import com.library.Entity.Book;
import com.library.Repository.AuthorRepository;
import com.library.Repository.BookRepository;
import com.library.Repository.GenreRepository;
import com.library.Repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
    }
    public List<Book>ListOfBooks()
    {
        return bookRepository.findAll();
    }
    public Optional<Book> FindByID(int id)
    {
        return bookRepository.findById(id);
    }
    public List<Book>ListBooksOfAuthorAndSurname(String name,String surname)
    {
        return bookRepository.findBooksByAuthorIs(authorRepository.findAuthorsByNameAndSurnameIs(name,surname));
    }
    public List<Book>findAuthorsBySurnameIs(String surname)
    {
        return bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsBySurnameIs(surname));
    }
    public List<Book>ListBooksOfAuthor(String name)
    {
        return bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsByNameIs(name));
    }
    public List<Book>ListBooksOfGenre(String genre)
    {
        return bookRepository.findBooksByGenreIs(genreRepository.findGenresByGenreNameIs(genre));
    }
    public List<Book>ListBooksOfPublisher(String publisher)
    {
        return bookRepository.findBooksByPublisherIs(publisherRepository.findPublisherByPublisherNameIs(publisher));
    }
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    @Transactional
    public void Delete(int id)
    {
         bookRepository.deleteById(id);
    }

}
