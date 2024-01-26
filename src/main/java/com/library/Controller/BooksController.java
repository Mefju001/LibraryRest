package com.library.Controller;

import com.library.Entity.*;
import com.library.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/Books")
public class BooksController {
    private final BookService bookService;
    private final BookLibraryService bookLibraryService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    @Autowired
    public BooksController(BookService bookService, BookLibraryService bookLibraryService, GenreService genreService, AuthorService authorService, PublisherService publisherService)
    {
        this.bookService = bookService;
        this.bookLibraryService = bookLibraryService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }


    @GetMapping("")
    public List<Book> ListOfBooks()
    {
        return bookService.ListOfBooks();
    }
    @GetMapping("/Library")
    public List<BookLibrary> ListOfBooksInLibrary()
    {
        return bookLibraryService.ListOfBooksInLibrary();
    }
    @GetMapping("/Library/{id}")
    public List<BookLibrary> ListOfBooksInLibraryByID(@PathVariable int id)
    {
        return bookLibraryService.ListOfBooksInLibraryByID(id);
    }
    @GetMapping("/Library/Book/{id}")
    public List<BookLibrary> ListOfBooksInLibraryByBook(@PathVariable int id)
    {
        return bookLibraryService.ListOfBooksInLibraryByBook(id);
    }
    @GetMapping("/{id}")
    public Optional<Book> FindSingleBook(@PathVariable int id)
    {
        return bookService.FindByID(id);
    }
    @GetMapping("/Sortby/{Sort}/{Type}")
    public List<Book>Sortbook(@PathVariable String Sort, @PathVariable String Type)
    {
        if(Objects.equals(Sort, "Price"))
        {
            if(Objects.equals(Type,"Asc"))
                return bookService.ListBooksSortPrice("Asc");
            if(Objects.equals(Type,"Desc"))
                return bookService.ListBooksSortPrice("Desc");
        } else if (Objects.equals(Sort, "Title"))
        {
            if(Objects.equals(Type,"Asc"))
                return bookService.ListBooksSortTitle("Asc");
            if(Objects.equals(Type,"Desc"))
                return bookService.ListBooksSortTitle("Desc");
        }
        return Collections.emptyList();
    }
    @GetMapping("/Author")
    public List<Book> listBooksOfAuthorNameAndSurname(@RequestParam String nameAndSurname){
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
    @GetMapping("/SearchBy")
    public List<Book> listBooksOfGenre(@RequestParam String Searchname){
        if(bookService.ListBooksOfGenre(Searchname).isEmpty()) {
            if (bookService.ListBooksOfPublisher(Searchname).isEmpty())
            {
                if (bookService.ListBooksForTitle(Searchname).isEmpty())
                {
                    return Collections.emptyList();
                }
                return bookService.ListBooksForTitle(Searchname);
            }

            return bookService.ListBooksOfPublisher(Searchname);
        }
        return bookService.ListBooksOfGenre(Searchname);
    }
    @GetMapping("/Price")
    public List<Book> listBooksOfPrice(@RequestParam(name = "number1") int number1,
                                           @RequestParam(name = "number2") int number2){
        try {
            // Wywołaj usługę z przekonwertowanymi wartościami
            return bookService.ListBooksForPrice((float) number1, (float) number2);
        } catch (NumberFormatException e) {
            // Zwróć odpowiedź błędu lub pusta listę, w zależności od wymagań
            return Collections.emptyList();
        }
    }
    @GetMapping("/Year")
    public List<Book> listBooksOfYear(@RequestParam(name = "number1") int Year1,
                                           @RequestParam(name = "number2") int Year2){
        try {
            return bookService.ListBooksForYear(Year1, Year2);
        } catch (NumberFormatException e) {
            // Zwróć odpowiedź błędu lub pusta listę, w zależności od wymagań
            return Collections.emptyList();
        }
    }




    /////

    @PostMapping("/AddBook")
    public ResponseEntity<String> AddBook(@RequestBody Book book) {
        try {
            // Sprawdź, czy gatunek istnieje
            Genre existingGenre = genreService.findGenresByGenreNameIs(book.getGenre().getGenreName());
            Author existingAuthors = authorService.findAuthorsByNameAndSurnameIs(book.getAuthor().getName(),book.getAuthor().getSurname());
            Publisher existingPublisher = publisherService.findPublisherByPublisherNameIs(book.getPublisher().getPublisherName());

            if (existingGenre == null) {
                // Jeśli gatunek nie istnieje, dodaj go
                existingGenre = genreService.save(book.getGenre());
                System.out.println("Genre");
            }
            if (existingAuthors== null) {
                // Jeśli autor nie istnieje, dodaj go
                existingAuthors = authorService.save(book.getAuthor());
                System.out.println("Author");
            }
            if (existingPublisher == null) {
                // Jeśli wydawca nie istnieje, dodaj go
                existingPublisher = publisherService.save(book.getPublisher());
                System.out.println("Publisher");
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
