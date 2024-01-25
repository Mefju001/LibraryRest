package com.library.Repository;

import com.library.Entity.BookLibrary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookLibraryRepository extends JpaRepository<BookLibrary,Integer> {

    List<BookLibrary> findByLibrary_ID(int id);
    List<BookLibrary> findByBook_ID(int id);
}
