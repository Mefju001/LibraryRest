package com.library.Controller;

import com.library.Entity.FavoriteUser;
import com.library.Entity.User;
import com.library.Service.FavoriteUserService;
import com.library.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/User")
public class UserController {

    private final FavoriteUserService favorite_userService;
    private final UserRoleService userRoleService;
    @Autowired
    public UserController(FavoriteUserService favoriteUser, UserRoleService userRoleService) {
        this.favorite_userService = favoriteUser;
        this.userRoleService = userRoleService;
    }

    @GetMapping("/FavoriteBook/{Username}")
    public List<FavoriteUser> ListOfBooksInLibrary(@PathVariable String Username)
    {
        return favorite_userService.ListOfFavoriteBooksByUsername(Username);
    }

////

    @PostMapping("/ChangePassword")
    public void ChangePassword(@RequestParam String Username, @RequestParam String Oldpassword, @RequestParam String Newpassword){
        userRoleService.changePassword(Username,Oldpassword,Newpassword);
        System.out.println(userRoleService.UserPass(Username));
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
