package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
    private String userId;
    private String userName;   // Denormalized for fast display
    private String userEmail;  // Denormalized for fast display
    private String status;     // "PENDING", "ACCEPTED", "REJECTED"
}
