/*package com.library.Controller;

import com.library.Entity.FavoriteUser;
import com.library.Service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    private final UserRoleService userRoleService;
    @Autowired
    public UserController( UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("/{Username}")
    public ResponseEntity<List<FavoriteUser>> ListOfBooksInLibrary(@PathVariable String Username)
    {
        return userRoleService.ListOfFavoriteBooksByUsername(Username);
    }

////

    @PostMapping("/ChangePassword")
    public ResponseEntity<HttpStatus> ChangePassword(@RequestParam String Username, @RequestParam String Oldpassword, @RequestParam String Newpassword){
        return userRoleService.changePassword(Username,Oldpassword,Newpassword);
    }
    @PostMapping("/AddBookToFavoriteList")
    public ResponseEntity<HttpStatus> AddbooktoFavoriteList(@RequestBody FavoriteUser favoriteUser){
        return userRoleService.AddBooksToFavoriteList(favoriteUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> DeleteBook(@PathVariable int id)
    {
        return userRoleService.Delete(id);
    }
}*/
