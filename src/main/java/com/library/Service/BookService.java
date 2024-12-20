package com.library.Service;

import com.library.Entity.Author;
import com.library.Entity.Book;
import com.library.Entity.Genre;
import com.library.Entity.Publisher;
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
    public List<Book> FindByID(int id)
    {
        return bookRepository.findById(id)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }
    public List<Book>ListBooksBySort(String sort, String type)
    {
        if(sort.equalsIgnoreCase("price"))
        {
            if(type.equalsIgnoreCase("asc"))
                return bookRepository.findByOrderByPriceAsc();
            else if(type.equalsIgnoreCase("desc"))
                return bookRepository.findByOrderByPriceDesc();
        } else if (sort.equalsIgnoreCase("title"))
        {
            if(type.equalsIgnoreCase("asc"))
                return bookRepository.findByOrderByTitleAsc();
            else if(type.equalsIgnoreCase("desc"))
                return bookRepository.findByOrderByTitleDesc();
        }
        return Collections.emptyList();
    }
    public List<Book>ListBooksOfAuthorAndSurname(String nameAndsurname)
    {
        String[] parts = nameAndsurname.split("\\s+");
        if (parts.length >= 2) {
            String name = parts[0];
            String surname = parts[1];
            return bookRepository.findBooksByAuthorIs(authorRepository.findAuthorsByNameAndSurnameIs(name, surname));
        }else if (parts.length == 1) {
        String name = parts[0];
        if(bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsByNameIs(name)).isEmpty()){
            return bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsBySurnameIs(name));
        }
        else{
            return bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsByNameIs(name));
        }
        } else {
            // Zwróć odpowiedź błędu lub pusta listę, w zależności od wymagań
            return Collections.emptyList();
        }
    }
    public List<Book>ListbooksOfSearchName(String searchname)
    {
        if(bookRepository.findBooksByGenreIs(genreRepository.findGenresByGenreNameIs(searchname)).isEmpty())
        {
         if(bookRepository.findBooksByTitleIs(searchname).isEmpty())
         {
             if(bookRepository.findBooksByPublisherIs(publisherRepository.findPublisherByPublisherNameIs(searchname)).isEmpty())
             {
                 return Collections.emptyList();
             }
             return bookRepository.findBooksByPublisherIs(publisherRepository.findPublisherByPublisherNameIs(searchname));
         }
         return bookRepository.findBooksByTitleIs(searchname);
        }
        return bookRepository.findBooksByGenreIs(genreRepository.findGenresByGenreNameIs(searchname));
    }
    public List<Book>ListBooksForPrice(Float number1, Float number2)
    {
        if(number1 != null && number2 != null && number1 > 0 && number2<500&&number1<number2)
            return bookRepository.findBooksByPriceIsBetween(number1,number2);
        else
            return Collections.emptyList();
    }
    public List<Book>ListBooksForYear(Integer Year, Integer Year2)
    {
        LocalDate date = LocalDate.now();
        if(Year != null && Year2 != null && Year >1493&& Year2<=date.getYear()&&Year<Year2)
            return bookRepository.findBooksByPublicationYearBetween(Year,Year2);
        else
            return Collections.emptyList();
    }

    @Transactional
    public Book save(Book book) {
        Genre existingGenre = genreRepository.findGenresByGenreNameIs(book.getGenre().getGenreName());
        Author existingAuthors = authorRepository.findAuthorsByNameAndSurnameIs(book.getAuthor().getName(),book.getAuthor().getSurname());
        Publisher existingPublisher = publisherRepository.findPublisherByPublisherNameIs(book.getPublisher().getPublisherName());
        if(existingGenre==null)
        {
            existingGenre = genreRepository.save(book.getGenre());
        }
        if(existingAuthors==null)
        {
            existingAuthors = authorRepository.save(book.getAuthor());
        }
        if(existingPublisher==null)
        {
            existingPublisher = publisherRepository.save(book.getPublisher());
        }
        book.setGenre(existingGenre);
        book.setAuthor(existingAuthors);
        book.setPublisher(existingPublisher);
        return bookRepository.save(book);
    }
    @Transactional
    public void Delete(int id)
    {
         bookRepository.deleteById(id);
    }

}
