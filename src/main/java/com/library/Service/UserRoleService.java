/*package com.library.Service;

import com.library.Entity.FavoriteUser;
import com.library.Repository.FavoriteUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
public class UserRoleService{
    private final UserAndRoleRepository userAndRoleRepository;
    private final FavoriteUserRepository favorite_user;
    private final UserRepository userRepository;
    @Autowired
    public UserRoleService(UserAndRoleRepository userAndRoleRepository, FavoriteUserRepository favoriteUser, UserRepository userRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
        favorite_user = favoriteUser;
        this.userRepository = userRepository;
    }
    @Transactional
    public ResponseEntity<HttpStatus> changePassword(String username, String oldPassword, String newPassword) {
        oldPassword = "{noop}"+oldPassword;
        newPassword = "{noop}"+newPassword;
        User user = userRepository.findById(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        if (!oldPassword.equals(user.getPassword())) {
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        user.setPassword(newPassword);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
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
}*/
