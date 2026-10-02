package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @PostMapping("/events/register")
    public String registerForEvent(@RequestParam("eventId") String eventId,
                                   @RequestParam("name") String name,
                                   @RequestParam("email") String email,
                                   RedirectAttributes redirectAttributes) {
        
        // 1. Find or create user based on email
        Optional<User> optionalUser = userService.getUserByEmail(email);
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setRole("ATTENDEE");
            user = userService.createUser(user);
        }

        // 2. Submit booking request (starts as PENDING status, does not block a seat yet)
        boolean success = eventService.requestBooking(eventId, user.getId(), user.getName(), user.getEmail());
        
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Booking request submitted successfully! Pending approval from Event Organizer.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Request failed! You have already submitted a request for this event package.");
        }
        
        return "redirect:/home";
    }
}
