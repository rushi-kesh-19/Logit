package com.example.springbootpractice.services;

import com.example.springbootpractice.api.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WeatherService {

    String api= "https://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=CITY";
    String accessKey = "9a4e6657fdf46cc8cb7024f306ed1391";

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city){
        String uapi = api.replace("YOUR_ACCESS_KEY", accessKey).replace("CITY", city);
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(uapi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse response = responseEntity.getBody();
        return response;
    }

}
