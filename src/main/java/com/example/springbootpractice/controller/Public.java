package com.example.springbootpractice.controller;

import com.example.springbootpractice.api.TextToSpeechRequest;
import com.example.springbootpractice.api.WeatherResponse;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.UserRepository;
import com.example.springbootpractice.services.UserService;
import com.example.springbootpractice.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    /**
     * Returns an MP3 response that browsers can play directly.
     */
    @PostMapping(value = "/text-to-speech", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "audio/mpeg")
    public ResponseEntity<byte[]> textToSpeech(@RequestBody TextToSpeechRequest request) {
        if (request == null || request.text() == null || request.text().isBlank()
                || request.voiceId() == null || request.voiceId().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        byte[] audio = weatherService.convertTextToSpeech(request);
        if (audio == null || audio.length == 0) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("audio/mpeg"))
                .contentLength(audio.length)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"speech.mp3\"")
                .body(audio);
    }
}
