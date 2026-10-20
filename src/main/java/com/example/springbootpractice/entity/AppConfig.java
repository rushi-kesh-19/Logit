package com.example.springbootpractice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("config")
@Data
@NoArgsConstructor
public class AppConfig {
    @Id
    ObjectId id;
    String key;
    String value;

}
