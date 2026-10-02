package com.example.demo.serviceimpl;

import com.example.demo.entity.Event;
import com.example.demo.entity.Registration;
import com.example.demo.repository.EventRepository;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        event.setAvailableSeats(event.getCapacity());
        if (event.getCategory() == null || event.getCategory().isEmpty()) {
            event.setCategory("OTHER");
        }
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }

    @Override
    public synchronized boolean requestBooking(String eventId, String userId, String userName, String userEmail) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            
            // Check if user is already in the list
            boolean exists = event.getRegistrations().stream()
                    .anyMatch(r -> r.getUserId().equals(userId));
            
            if (exists) {
                return false; // Already registered/requested
            }
            
            // Add a new registration request with status PENDING
            Registration registration = new Registration(userId, userName, userEmail, "PENDING");
            event.getRegistrations().add(registration);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean acceptBooking(String eventId, String userId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            
            // 1. Check capacity
            if (event.getAvailableSeats() <= 0) {
                return false; // Sold out
            }
            
            // 2. Find registration and update status
            for (Registration reg : event.getRegistrations()) {
                if (reg.getUserId().equals(userId) && "PENDING".equals(reg.getStatus())) {
                    reg.setStatus("ACCEPTED");
                    event.setAvailableSeats(event.getAvailableSeats() - 1);
                    eventRepository.save(event);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public synchronized boolean rejectBooking(String eventId, String userId) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            
            for (Registration reg : event.getRegistrations()) {
                if (reg.getUserId().equals(userId)) {
                    // If it was already accepted, return the seat
                    if ("ACCEPTED".equals(reg.getStatus())) {
                        event.setAvailableSeats(event.getAvailableSeats() + 1);
                    }
                    reg.setStatus("REJECTED");
                    eventRepository.save(event);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Event> getEventsByAttendeeEmail(String email) {
        List<Event> allEvents = eventRepository.findAll();
        List<Event> attendeeEvents = new ArrayList<>();
        for (Event event : allEvents) {
            boolean matches = event.getRegistrations().stream()
                    .anyMatch(r -> r.getUserEmail().equalsIgnoreCase(email));
            if (matches) {
                attendeeEvents.add(event);
            }
        }
        return attendeeEvents;
    }
}
