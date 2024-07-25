package com.library.Service;

import com.library.Entity.User;
import com.library.Repository.UserAndRoleRepository;
import com.library.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleService{
    private final UserAndRoleRepository userAndRoleRepository;
    private final UserRepository userRepository;
    public UserRoleService(UserAndRoleRepository userAndRoleRepository, UserRepository userRepository) {
        this.userAndRoleRepository = userAndRoleRepository;
        this.userRepository = userRepository;
    }
    public Optional<User> UserPass(String username)
    {
        return userRepository.findById(username);
    };
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        oldPassword = "{noop}"+oldPassword;        newPassword = "{noop}"+newPassword;

        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!oldPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
