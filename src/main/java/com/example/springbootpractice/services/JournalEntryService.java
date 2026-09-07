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
import java.util.stream.Collectors;

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
    public boolean deleteEntryById(ObjectId id, String username) {
        try {
            User user = userService.getUserbyUsername(username);
            boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.updateUser(user, username);//can also do userRepository.save(user)
                journalEntryRepository.deleteById(id);
                return true;
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return false;
    }

    @Transactional
    public boolean editEntry(ObjectId id, JournalEntry newentry, String username) {
        JournalEntry old = userService.getUserbyUsername(username).getJournalEntries().stream().filter(x->x.getId().equals(id)).findAny().orElse(null);
        if (old != null){
            old.setTitle(!newentry.getTitle().equals("") ? newentry.getTitle(): old.getTitle());
            old.setDesc(newentry.getDesc()!= null && !newentry.getDesc().equals("") ? newentry.getDesc(): old.getDesc());
            journalEntryRepository.save(old);
            return true;
        }
        return false;
    }

    public boolean checkEntryInUser(User user, ObjectId id) {
        return user.getJournalEntries().stream().anyMatch(x -> id.equals(x.getId()));
    }
}
