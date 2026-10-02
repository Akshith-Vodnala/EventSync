package com.example.demo.service;

import com.example.demo.entity.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
    Event createEvent(Event event);
    List<Event> getAllEvents();
    Optional<Event> getEventById(String id);
    void deleteEvent(String id);
    
    // Modern approval-based registration service methods
    boolean requestBooking(String eventId, String userId, String userName, String userEmail);
    boolean acceptBooking(String eventId, String userId);
    boolean rejectBooking(String eventId, String userId);
    
    // Status checker query method
    List<Event> getEventsByAttendeeEmail(String email);
}
