package com.example.springbootpractice.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<Long, JournalEntry>();
    @GetMapping
    public List<JournalEntry> getJournal() {
        return new ArrayList<>(journalEntries.values());
    }
    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntry getjournalbyid(@PathVariable Long myid){
        return journalEntries.get(myid);
    }

    @PutMapping("id/{myid}")
    public boolean editjournal(@PathVariable Long myid, @RequestBody JournalEntry entry) {
        journalEntries.put(myid, entry);
        return true;
    }
}
