package com.library.Service;

import com.library.Entity.Book;
import com.library.Repository.AuthorRepository;
import com.library.Repository.BookRepository;
import com.library.Repository.GenreRepository;
import com.library.Repository.PublisherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
    public List<Book>ListBooksForTitle(String title)
    {
        return bookRepository.findBooksByTitleIs(title);
    }
    public List<Book>ListBooksForPrice(Float number1, Float number2)
    {
        if(number1 > 0 && number2<500&&number1<number2)
            return bookRepository.findBooksByPriceIsBetween(number1,number2);
        else
            return Collections.emptyList();

    }
    public List<Book>ListBooksForYear(int Year, int Year2)
    {
        LocalDate date = LocalDate.now();
        if(Year >1493&& Year2<=date.getYear()&&Year<Year2)
            return bookRepository.findBooksByPublicationYearBetween(Year,Year2);
        else
            return Collections.emptyList();
    }
    public List<Book>ListBooksSortPrice(String type)
    {
        if(Objects.equals(type, "Asc"))
            return bookRepository.findByOrderByPriceAsc();
        else if(Objects.equals(type, "Desc"))
            return bookRepository.findByOrderByPriceDesc();
        else
            return null;
    }
    public List<Book>ListBooksSortTitle(String type)
    {
        if(Objects.equals(type, "Asc"))
            return bookRepository.findByOrderByTitleAsc();
        else if(Objects.equals(type, "Desc"))
            return bookRepository.findByOrderByTitleDesc();
        else
            return null;
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
