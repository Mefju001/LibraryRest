package com.library.Controller;

import com.library.Entity.Author;
import com.library.Entity.Book;
import com.library.Entity.Genre;
import com.library.Entity.Publisher;
import com.library.Service.AuthorService;
import com.library.Service.BookService;
import com.library.Service.GenreService;
import com.library.Service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class BooksController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    private final PublisherService publisherService;
    @Autowired
    public BooksController(BookService bookService, GenreService genreService, AuthorService authorService, PublisherService publisherService)
    {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    @GetMapping("/Books")
    public List<Book> ListOfBooks()
    {
        return bookService.ListOfBooks();
    }
    @GetMapping("/Books/{id}")
    public Optional<Book> FindSingleBook(@PathVariable int id)
    {
        return bookService.FindByID(id);
    }
    @GetMapping("/Books/Author/{nameAndSurname}")
    public List<Book> listBooksOfAuthorNameAndSurname(@PathVariable String nameAndSurname){
        String[] parts = nameAndSurname.split("\\s+"); // "\\s+" oznacza dowolny biały znak (np. spacja)
        if (parts.length >= 2) {
            String name = parts[0];
            String surname = parts[1];

            // Wywołanie metody serwisu, przekazując name i surname
            return bookService.ListBooksOfAuthorAndSurname(name,surname);
        } else if (parts.length == 1) {
            String name = parts[0];
            if(bookService.ListBooksOfAuthor(name).isEmpty()){
                return bookService.findAuthorsBySurnameIs(name);
            }
            else{
                return bookService.ListBooksOfAuthor(name);
            }
        } else {
            // Zwróć odpowiedź błędu lub pusta listę, w zależności od wymagań
            return Collections.emptyList();
        }
    }
    @GetMapping("/Books/Genre/{genre}")
    public List<Book> listBooksOfGenre(@PathVariable String genre){
        return bookService.ListBooksOfGenre(genre);
    }
    @GetMapping("/Books/Publisher/{publisher}")
    public List<Book> listBooksOfPublisher(@PathVariable String publisher){
        return bookService.ListBooksOfPublisher(publisher);
    }

    /////

    @PostMapping("/Books/AddBook")
    public ResponseEntity<String> AddBook(@RequestBody Book book) {
        try {
            // Sprawdź, czy gatunek istnieje
            Genre existingGenre = genreService.findGenresByGenreNameIs(book.getGenre().getGenreName());
            Author existingAuthors = authorService.findAuthorsByNameAndSurnameIs(book.getAuthor().getName(),book.getAuthor().getSurname());
            Publisher existingPublisher = publisherService.findPublisherByPublisherNameIs(book.getPublisher().getPublisherName());

            if (existingGenre == null) {
                // Jeśli gatunek nie istnieje, dodaj go
                existingGenre = genreService.save(book.getGenre());
                System.out.println("a");
            }
            if (existingAuthors== null) {
                // Jeśli autor nie istnieje, dodaj go
                existingAuthors = authorService.save(book.getAuthor());
                System.out.println("a");
            }
            if (existingPublisher == null) {
                // Jeśli wydawca nie istnieje, dodaj go
                existingPublisher = publisherService.save(book.getPublisher());
                System.out.println("a");
            }

// Ustaw gatunek na istniejący gatunek lub nowo dodany
            book.setGenre(existingGenre);
            book.setAuthor(existingAuthors);
            book.setPublisher(existingPublisher);


            // Dodaj książkę
            bookService.save(book);

            return ResponseEntity.ok("Książka została dodana.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas dodawania książki.");
        }
    }
    @PutMapping("/Books/UpdateBook")
    public Book UpdateBook(@RequestBody Book book)
    {
        return bookService.save(book);
    }
    @DeleteMapping("/Books/Delete/{id}")
    public void DeleteBook(@PathVariable int id)
    {
        bookService.Delete(id);
    }

}
