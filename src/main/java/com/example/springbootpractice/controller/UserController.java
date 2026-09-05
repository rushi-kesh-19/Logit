package com.example.springbootpractice.controller;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.services.JournalEntryService;
import com.example.springbootpractice.services.UserDetailsServiceImpl;
import com.example.springbootpractice.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public List<User> getusers(){
        return userService.getUsers();
    }

    @GetMapping("/user")
    public User getuser(@PathVariable User user){
        return userService.getUserbyUsername(user.getUsername());
    }

    @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User old = userService.getUserbyUsername(username);

            old.setUsername(user.getUsername());
            old.setPassword(user.getPassword());
            userService.saveUser(old);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
