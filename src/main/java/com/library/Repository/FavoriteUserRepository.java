package com.library.Repository;

import com.library.Entity.FavoriteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteUserRepository extends JpaRepository<FavoriteUser,Integer> {
    List<FavoriteUser> findByUser_Username(String username);

}
