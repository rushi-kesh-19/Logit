package com.example.springbootpractice.services;

import com.example.springbootpractice.api.TextToSpeechRequest;
import com.example.springbootpractice.api.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Component
public class WeatherService {

    String api= "https://api.weatherstack.com/current?access_key=YOUR_ACCESS_KEY&query=CITY";
    String accessKey = "9a4e6657fdf46cc8cb7024f306ed1391";

    private static final String ELEVENLABS_TTS_URL =
            "https://api.elevenlabs.io/v1/text-to-speech/{voiceId}?output_format=mp3_44100_128";

    @Value("${elevenlabs.api-key:}") // @Value("${eleven_api}")
    private String elevenLabsApiKey;

    @Autowired
    private RestTemplate restTemplate;


    public WeatherResponse getWeather(String city){
        String uapi = api.replace("YOUR_ACCESS_KEY", accessKey).replace("CITY", city);
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(uapi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse response = responseEntity.getBody();
        return response;
    }

    //THe following code is just for example, it wont work since weather api does not have any POST api

    public WeatherResponse postWeather(){
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("key", "value");

        String requestBody= "{\"username\":\"Ram\", \"password\":\"Ram\"}";
        HttpEntity<String> httpEntity= new HttpEntity<>(requestBody, httpHeader);
        // you can send the user details by creating an user object with those properties(username & password) and do HttpEntity<User>

        ResponseEntity<WeatherResponse> responseEntity = restTemplate.exchange(api, HttpMethod.POST, httpEntity, WeatherResponse.class);
        return responseEntity.getBody();
    }

    /**
     * Calls ElevenLabs' text-to-speech endpoint and returns the generated MP3.
     * The caller is responsible for sending the bytes to the client.
     */
    public byte[] convertTextToSpeech(TextToSpeechRequest request) {
        if (elevenLabsApiKey.isBlank()) {
            throw new IllegalStateException("ELEVENLABS_API_KEY is not configured");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.valueOf("audio/mpeg")));
        headers.set("xi-api-key", elevenLabsApiKey);

        Map<String, String> body = Map.of(
                "text", request.text(),
                "model_id", "eleven_multilingual_v2"
        );

        ResponseEntity<byte[]> response = restTemplate.exchange(
                ELEVENLABS_TTS_URL,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                byte[].class,
                request.voiceId()
        );

        return response.getBody();
    }



}
