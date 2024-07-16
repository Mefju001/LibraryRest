package com.library.Service;

import com.library.Entity.FavoriteUser;
import com.library.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteUserService {
    private final FavoriteUserRepository favorite_user;



    @Autowired
    public FavoriteUserService( FavoriteUserRepository favoriteUser) {

        this.favorite_user = favoriteUser;
    }

    public List<FavoriteUser> ListOfFavoriteBooks() {
        return favorite_user.findAll();
    }

    public FavoriteUser AddBooksToFavoriteList(FavoriteUser Favorite_user) {
        return favorite_user.save(Favorite_user);
    }
    public List<FavoriteUser> ListOfFavoriteBooksByUsername(String Username) {
        return favorite_user.findByUser_Username(Username);
    }

    @Transactional
    public void Delete(int id) {
        favorite_user.deleteById(id);
    }

}
