package com.library.Controller;

import com.library.Entity.FavoriteUser;
import com.library.Service.FavoriteUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    private final FavoriteUserService favorite_userService;
    @Autowired
    public UserController( FavoriteUserService favoriteUser) {
        this.favorite_userService = favoriteUser;
    }

    @GetMapping("/FavoriteBook/{Username}")
    public List<FavoriteUser> ListOfBooksInLibrary(@PathVariable String Username)
    {
        return favorite_userService.ListOfFavoriteBooksByUsername(Username);
    }
    @PostMapping("/AddBookToFavoriteList")
    public FavoriteUser AddbooktoFavoriteList(@RequestBody FavoriteUser favoriteUser){
        return favorite_userService.AddBooksToFavoriteList(favoriteUser);
    }
    @DeleteMapping("/Delete/{id}")
    public void DeleteBook(@PathVariable int id)
    {
        favorite_userService.Delete(id);
    }
}
