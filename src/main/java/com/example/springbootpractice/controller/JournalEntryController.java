package com.example.springbootpractice.controller;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.repository.JournalEntryRepository;
import com.example.springbootpractice.services.JournalEntryService;
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

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournal() {
        try {
            return new ResponseEntity<>(journalEntryService.getEntries(), HttpStatus.OK);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry entry) {
        journalEntryService.saveEntry(entry);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<JournalEntry> getjournalbyid(@PathVariable ObjectId myid){
        return new ResponseEntity<JournalEntry>( journalEntryService.getEntrybyId(myid), HttpStatus.OK);
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<?> editjournal(@PathVariable ObjectId myid, @RequestBody JournalEntry newentry) {
        JournalEntry old = journalEntryService.getEntrybyId(myid);

        if (old != null){
            old.setTitle(!newentry.getTitle().equals("") ? newentry.getTitle(): old.getTitle());
            old.setDesc(newentry.getDesc()!= null && !newentry.getDesc().equals("") ? newentry.getDesc(): old.getDesc());
            journalEntryRepository.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        journalEntryService.deleteEntry(id, username);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

}
