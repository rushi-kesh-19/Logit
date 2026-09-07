package com.example.springbootpractice.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data // lombok handles getting and setting of the values, so u can add conditions/rules along with annotations
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id ;
    @Indexed(unique = true)//faster lookups
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    public List<JournalEntry> journalEntries = new ArrayList<>(); // when initialized this should not be null , should be [] array
    public List<String> roles;
}
