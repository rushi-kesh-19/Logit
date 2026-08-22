package com.example.springbootpractice.controller;

import com.example.springbootpractice.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getJournal() {
        return null;
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry entry) {
        journalEntryService.saveEntry(entry);
        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntry getjournalbyid(@PathVariable Long myid){
        return null;
    }

    @PutMapping("id/{myid}")
    public boolean editjournal(@PathVariable Long myid, @RequestBody JournalEntry entry) {
        return true;
    }
}
