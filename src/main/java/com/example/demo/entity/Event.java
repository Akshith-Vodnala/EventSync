package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDate date;
    private LocalTime time;
    private String venue;
    private double price;
    private int capacity;
    private int availableSeats;
    private String category; // "WEDDING", "BIRTHDAY", "CONFERENCE", "PARTY", "OTHER"
    
    // NoSQL Document pattern: Embed registration documents directly inside the event document
    private List<Registration> registrations = new ArrayList<>();
}
