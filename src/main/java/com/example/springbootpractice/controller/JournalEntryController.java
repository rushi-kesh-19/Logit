package com.example.springbootpractice.controller;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.JournalEntryRepository;
import com.example.springbootpractice.services.JournalEntryService;
import com.example.springbootpractice.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournal() {
        try {
            return new ResponseEntity<>(journalEntryService.getEntries(), HttpStatus.OK);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntry> getjournalbyid(@PathVariable ObjectId id){
        if (journalEntryService.getEntrybyId(id) != null) {
            return new ResponseEntity<JournalEntry>(journalEntryService.getEntrybyId(id), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> editjournal(@PathVariable ObjectId id, @RequestBody JournalEntry newentry) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean edited = journalEntryService.editEntry(id, newentry, username);
        if (edited){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody JournalEntry newEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        journalEntryService.setEntry(username, newEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntry (@PathVariable ObjectId id){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteEntryById(id, username);

        if (removed) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
