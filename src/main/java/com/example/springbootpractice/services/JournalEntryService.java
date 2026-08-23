package com.example.springbootpractice.services;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getEntries(){
        return journalEntryRepository.findAll();
    }
    public JournalEntry getEntrybyId(ObjectId myid){// optional<JournalEntry> can be used instead of orElse to handle null
        return journalEntryRepository.findById(myid).orElse(null);
    }

}
