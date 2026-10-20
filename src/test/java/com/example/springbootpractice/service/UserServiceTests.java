package com.example.springbootpractice.service;

import com.example.springbootpractice.services.WeatherService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {


    @Autowired
    WeatherService weatherService;

    //@Disabled
    @Test
    public void test(){
        assertEquals(weatherService.getWeather("Mumbai").getCurrent().getFeelslike(), 35);
    }

    @ParameterizedTest
    @CsvSource({
        "1,1,2",
            "3,3,9"
    })
    public void test2(int a, int b, int expected){
        assertEquals(expected, a+b);
    }
}
