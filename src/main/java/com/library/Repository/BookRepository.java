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
    Book findBookByIsbnIs(String isbn);
    List<Book> findBooksByGenreIs(Genre genre);
    List<Book> findBooksByPublisherIs(Publisher publisher);
    List<Book> findBooksByTitleIs(String title);
    List<Book> findBooksByAuthorIn(List<Author> authors);
    List<Book> findBooksByPriceIsBetween(float price, float price2);
    List<Book> findBooksByPublicationYearBetween(int Year, int Year2);
    List<Book> findByOrderByPriceAsc();
    List<Book> findByOrderByPriceDesc();
    List<Book> findByOrderByTitleAsc();
    List<Book> findByOrderByTitleDesc();

}
