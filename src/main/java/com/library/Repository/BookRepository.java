package com.library.Repository;

import com.library.Entity.Author;
import com.library.Entity.Book;
import com.library.Entity.Genre;
import com.library.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findBooksByAuthorIs(Author author);
    List<Book> findBooksByGenreIs(Genre genre);
    List<Book> findBooksByPublisherIs(Publisher publisher);

    List<Book> findBooksByAuthorIn(List<Author> authors);
}
