package com.example.springbootpractice.controller;

import com.example.springbootpractice.api.WeatherResponse;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.services.UserService;
import com.example.springbootpractice.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class Public {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<String> hello(){
        WeatherResponse weatherData = weatherService.getWeather("Mumbai");
        String feels = "";
        if (weatherData != null) {
            feels = "Today feels like" + weatherData.current.getFeelslike();
        }
        return new ResponseEntity<>("Hello World" + feels, HttpStatus.OK);
    }

    @PostMapping("user")
    public void saveUser(@RequestBody User user){
        userService.saveUser(user);
    }
}
