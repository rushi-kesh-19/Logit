package com.example.springbootpractice.api;

/**
 * Text and ElevenLabs voice to synthesize. The voice ID can be obtained from
 * the ElevenLabs voice library or the Get voices API.
 */
public record TextToSpeechRequest(String text, String voiceId) {
}
