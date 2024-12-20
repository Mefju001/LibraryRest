package com.library.Service;

import com.library.Entity.Book;
import com.library.Entity.BookLibrary;
import com.library.Entity.Library;
import com.library.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<Library>> ListOfLibrary() {
        if(libraryRepository.findAll().isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(libraryRepository.findAll(),HttpStatus.OK);
    }

    public ResponseEntity<Library> AddLibrary(Library library) {
        try {
            if (libraryRepository.findLibraryByAdresBibliotekiIs(library.getAdresBiblioteki())!=null||libraryRepository.findLibraryByNazwaBibliotekiIs(library.getNazwaBiblioteki())!=null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            Library savedLibrary = libraryRepository.save(library);

            return new ResponseEntity<>(savedLibrary, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<BookLibrary>> ListOfBooksInLibrary() {
        try{
            return new ResponseEntity<>(bookLibraryRepository.findAll(),HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<BookLibrary>> ListOfBooksInLibraryByID(int id) {
        try{
            List<BookLibrary> bookLibrary = bookLibraryRepository.findByLibrary_ID(id);
            if(bookLibrary.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookLibrary,HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<HttpStatus> DeleteLibrary(int id) {
        try {
            if(bookLibraryRepository.findByLibrary_ID(id).isEmpty()) {
                libraryRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else{
                List<BookLibrary> booksInLibrary = bookLibraryRepository.findByLibrary_ID(id);
                for (BookLibrary book : booksInLibrary) {
                    bookLibraryRepository.deleteById(book.getID());
                }
                // Usunięcie samej biblioteki
                libraryRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<BookLibrary> AddBookToLibrary(BookLibrary bookLibrary) {
        try {
            BookLibrary booklibrary = bookLibraryRepository.save(bookLibrary);

            return new ResponseEntity<>(booklibrary, HttpStatus.CREATED);

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
