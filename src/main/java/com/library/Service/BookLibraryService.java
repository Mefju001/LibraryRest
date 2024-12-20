package com.library.Service;

import com.library.Entity.BookLibrary;
import com.library.Entity.Library;
import com.library.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLibraryService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final BookLibraryRepository bookLibraryRepository;
    private final LibraryRepository libraryRepository;

    @Autowired
    public BookLibraryService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, PublisherRepository publisherRepository, BookLibraryRepository bookLibraryRepository, LibraryRepository libraryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.publisherRepository = publisherRepository;
        this.bookLibraryRepository = bookLibraryRepository;
        this.libraryRepository = libraryRepository;
    }
    public List<Library> ListOfLibrary() {
        return libraryRepository.findAll();
    }

    public Library AddLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public List<BookLibrary> ListOfBooksInLibrary() {
        return bookLibraryRepository.findAll();
    }

    public List<BookLibrary> ListOfBooksInLibraryByID(int id) {
        return bookLibraryRepository.findByLibrary_ID(id);
    }

    @Transactional
    public void DeleteLibrary(int id) {
        if(bookLibraryRepository.findByLibrary_ID(id).isEmpty()) {
            libraryRepository.deleteById(id);
        }
        else{
            List<BookLibrary> booksInLibrary = bookLibraryRepository.findByLibrary_ID(id);
            for (BookLibrary book : booksInLibrary) {
                bookLibraryRepository.deleteById(book.getID());
            }
            // Usunięcie samej biblioteki
            libraryRepository.deleteById(id);
            }
    }

    @Transactional
    public BookLibrary AddBookToLibrary(BookLibrary bookLibrary) {
        return bookLibraryRepository.save(bookLibrary);
    }
}
