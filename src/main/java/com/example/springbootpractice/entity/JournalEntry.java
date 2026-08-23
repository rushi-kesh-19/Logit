package com.example.springbootpractice.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "journal_entries") // map table row to this
@Data
public class JournalEntry {

    @Id // mapping primary key, mongodb creates Objectid if not provided // If existing id is provided, the row data will be updated
    public ObjectId id ;
    @NonNull
    public String title;
    public String desc;
    public LocalDateTime date;


}
