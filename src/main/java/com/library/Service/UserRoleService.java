package com.library.Service;

import com.library.Entity.User;
import com.library.Repository.UserAndRoleRepository;
import com.library.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserRoleService{
    private final UserAndRoleRepository userAndRoleRepository;
    private final UserRepository userRepository;
    public UserRoleService(UserAndRoleRepository userAndRoleRepository, UserRepository userRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
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
}
