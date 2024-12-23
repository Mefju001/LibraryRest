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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    //Genre
    public Genre findGenresByGenreNameIs(String genreName)
    {
        return genreRepository.findGenresByGenreNameIs(genreName);
    }
    @Transactional
    public Genre save(Genre genre)
    {
        return genreRepository.save(genre);
    }
    //Author
    public Author findAuthorsByNameAndSurnameIs(String name, String surname)
    {
        return authorRepository.findAuthorsByNameAndSurnameIs(name,surname);
    }
    @Transactional
    public Author save(Author author)
    {
        return authorRepository.save(author);
    }
    //Publisher
    public Publisher findPublisherByPublisherNameIs(String publisherName) {
        return publisherRepository.findPublisherByPublisherNameIs(publisherName);
    }

    @Transactional
    public Publisher save(Publisher Publisher)
    {
        return publisherRepository.save(Publisher);
    }
    //Book
    public ResponseEntity<List<Book>> ListOfBooks()
    {
        try{
            List<Book>books = bookRepository.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    public ResponseEntity<Book> FindByID(int id)
    {
        Optional<Book>book = bookRepository.findById(id);
        if(book.isPresent())
        {
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
    public ResponseEntity<List<Book>>ListBooksBySort(String sort, String type)
    {
        List<Book>books;
        if(sort.equalsIgnoreCase("price"))
        {
            if(type.equalsIgnoreCase("asc")){
                books = bookRepository.findByOrderByPriceAsc();
                return new ResponseEntity<>(books,HttpStatus.OK);
            }
            else if(type.equalsIgnoreCase("desc")) {
                books = bookRepository.findByOrderByPriceDesc();
                return new ResponseEntity<>(books,HttpStatus.OK);
            }
        }
        else if (sort.equalsIgnoreCase("title")){
            if(type.equalsIgnoreCase("asc")){
                books = bookRepository.findByOrderByTitleAsc();
                return new ResponseEntity<>(books,HttpStatus.OK);
            }
            else if(type.equalsIgnoreCase("desc"))
            {
                books = bookRepository.findByOrderByTitleDesc();
                return new ResponseEntity<>(books,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }
    public ResponseEntity<List<Book>>ListBooksOfAuthorAndSurname(String nameAndsurname)
    {
        List<Book>books;
        String[] parts = nameAndsurname.split("\\s+");
        if (parts.length >= 2) {
            String name = parts[0];
            String surname = parts[1];
            books = bookRepository.findBooksByAuthorIs(authorRepository.findAuthorsByNameAndSurnameIs(name, surname));
            return new ResponseEntity<>(books,HttpStatus.OK);
        }else if (parts.length == 1) {
        String name = parts[0];
        if(bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsByNameIs(name)).isEmpty()){
            books = bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsBySurnameIs(name));
            return new ResponseEntity<>(books,HttpStatus.OK);
        }
        else{
            books = bookRepository.findBooksByAuthorIn(authorRepository.findAuthorsByNameIs(name));
            return new ResponseEntity<>(books,HttpStatus.OK);
        }
        } else {

            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<List<Book>>ListbooksOfSearchName(String searchname)
    {
        if(bookRepository.findBooksByGenreIs(genreRepository.findGenresByGenreNameIs(searchname)).isEmpty())
        {
         if(bookRepository.findBooksByTitleIs(searchname).isEmpty())
         {
             if(bookRepository.findBooksByPublisherIs(publisherRepository.findPublisherByPublisherNameIs(searchname)).isEmpty())
             {
                 return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
             }
             return new ResponseEntity<>(bookRepository.findBooksByPublisherIs(publisherRepository.findPublisherByPublisherNameIs(searchname)),HttpStatus.OK);
         }
         return new ResponseEntity<>(bookRepository.findBooksByTitleIs(searchname),HttpStatus.OK);
        }
        return new ResponseEntity<>(bookRepository.findBooksByGenreIs(genreRepository.findGenresByGenreNameIs(searchname)),HttpStatus.OK);
    }
    public ResponseEntity<List<Book>>ListBooksForPrice(Float number1, Float number2)
    {
        if(number1 != null && number2 != null && number1 > 0 && number2<500&&number1<number2)
            return new ResponseEntity<>(bookRepository.findBooksByPriceIsBetween(number1,number2),HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    public ResponseEntity<List<Book>>ListBooksForYear(Integer Year, Integer Year2)
    {
        LocalDate date = LocalDate.now();
        if(Year != null && Year2 != null && Year >1493&& Year2<=date.getYear()&&Year<Year2)
            return new ResponseEntity<>(bookRepository.findBooksByPublicationYearBetween(Year,Year2),HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<Book> save(Book book) {
        try {
            Genre existingGenre = genreRepository.findGenresByGenreNameIs(book.getGenre().getGenreName());
            Author existingAuthors = authorRepository.findAuthorsByNameAndSurnameIs(book.getAuthor().getName(), book.getAuthor().getSurname());
            Publisher existingPublisher = publisherRepository.findPublisherByPublisherNameIs(book.getPublisher().getPublisherName());
            if (existingGenre == null) {
                existingGenre = genreRepository.save(book.getGenre());
            }
            if (existingAuthors == null) {
                existingAuthors = authorRepository.save(book.getAuthor());
            }
            if (existingPublisher == null) {
                existingPublisher = publisherRepository.save(book.getPublisher());
            }
            book.setGenre(existingGenre);
            book.setAuthor(existingAuthors);
            book.setPublisher(existingPublisher);
            return new ResponseEntity<>(bookRepository.save(book),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.CONFLICT);
        }
    }
    @Transactional
    public ResponseEntity<HttpStatus> Delete(int id)
    {
        Optional<Book>book=bookRepository.findById(id);
        if(book.isPresent()) {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

}
