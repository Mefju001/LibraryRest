package com.library.Repository;

import com.library.Entity.Author;
import com.library.Entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Integer> {
    Library findLibraryByAdresBibliotekiIs(String adres);
    Library findLibraryByNazwaBibliotekiIs(String nazwa);
}
