package com.library.Repository;

import com.library.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Author findAuthorsByNameAndSurnameIs(String name, String surname);
    List<Author> findAuthorsByNameIs(String name);
    List<Author> findAuthorsBySurnameIs(String surname);


}
