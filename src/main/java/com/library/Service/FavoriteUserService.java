package com.library.Service;

import com.library.Entity.Book;
import com.library.Entity.FavoriteUser;
import com.library.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FavoriteUserService {
    private final FavoriteUserRepository favorite_user;



    @Autowired
    public FavoriteUserService( FavoriteUserRepository favoriteUser) {

        this.favorite_user = favoriteUser;
    }

    public ResponseEntity<List<FavoriteUser>> ListOfFavoriteBooks() {
        try{
            List<FavoriteUser>favoriteUsers = favorite_user.findAll();
            return new ResponseEntity<>(favoriteUsers, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
        }
    }

    public ResponseEntity<HttpStatus> AddBooksToFavoriteList(FavoriteUser Favoriteuser) {
        try{
            if(Favoriteuser == null||Favoriteuser.getUser()==null||Favoriteuser.getBook()==null)
            {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            favorite_user.save(Favoriteuser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<FavoriteUser>> ListOfFavoriteBooksByUsername(String Username) {
        try{
            List<FavoriteUser>favoriteUsers = favorite_user.findByUser_Username(Username);
            return new ResponseEntity<>(favoriteUsers, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NO_CONTENT);
        }
    }

    @Transactional
    public ResponseEntity<HttpStatus> Delete(int id) {
        try {
            if(favorite_user.existsById(id)) {
                favorite_user.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
