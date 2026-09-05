package com.example.springbootpractice.services;

import com.example.springbootpractice.entity.JournalEntry;
import com.example.springbootpractice.entity.User;
import com.example.springbootpractice.repository.JournalEntryRepository;
import com.example.springbootpractice.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

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

    @Transactional
    public void setEntry (String username, JournalEntry newentry ){

        newentry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(newentry);
        User old = userService.getUserbyUsername(username);
        old.getJournalEntries().add(saved);
        userRepository.save(old);
    }

    @Transactional
    public void deleteEntry(ObjectId id, String username) {
        User user = userService.getUserbyUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId() == id);
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);

    }

    public void editEntry(ObjectId id, JournalEntry newEntry) {
        JournalEntry old = journalEntryRepository.findById(id).orElse(null);

        if (old != null){
            old.setTitle(newEntry.getTitle());
            old.setDesc(newEntry.getDesc());
            journalEntryRepository.save(old);
        }
    }
}
