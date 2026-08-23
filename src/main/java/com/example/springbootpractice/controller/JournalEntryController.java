package com.example.springbootpractice.controller;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournal() {
        return new ResponseEntity<>(journalEntryService.getEntries(), HttpStatus.OK);
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
            old.setTitle(newentry.getTitle()!= null && !newentry.getTitle().equals("") ? newentry.getTitle(): old.getTitle());
            old.setDesc(newentry.getDesc()!= null && !newentry.getDesc().equals("") ? newentry.getDesc(): old.getDesc());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
