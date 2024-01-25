package com.library.Service;

import com.library.Entity.BookLibrary;
import com.library.Repository.BookLibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLibraryService {
    private final BookLibraryRepository bookLibraryRepository;
    @Autowired
    public BookLibraryService(BookLibraryRepository bookLibraryRepository) {
        this.bookLibraryRepository = bookLibraryRepository;
    }

    public List<BookLibrary>ListOfBooksInLibrary()
    {
        return bookLibraryRepository.findAll();
    }
    public List<BookLibrary>ListOfBooksInLibraryByID(int id)
    {
        return bookLibraryRepository.findByLibrary_ID(id);
    }
    public List<BookLibrary>ListOfBooksInLibraryByBook(int id)
    {
        return bookLibraryRepository.findByBook_ID(id);
    }
}
