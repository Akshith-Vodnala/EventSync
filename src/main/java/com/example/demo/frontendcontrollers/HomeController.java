package com.example.demo.frontendcontrollers;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private EventService eventService;

    @GetMapping({"/", "/home", "/index"})
    public String viewHomePage(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Event> events = eventService.getAllEvents();
        
        // Handle search query filtering
        if (search != null && !search.trim().isEmpty()) {
            String query = search.toLowerCase().trim();
            events = events.stream()
                    .filter(e -> e.getTitle().toLowerCase().contains(query) 
                              || e.getVenue().toLowerCase().contains(query)
                              || (e.getCategory() != null && e.getCategory().toLowerCase().contains(query)))
                    .collect(Collectors.toList());
            model.addAttribute("searchQuery", search);
        }
        
        model.addAttribute("events", events);
        return "home_templets/index";
    }

    @GetMapping("/about")
    public String viewAboutPage() {
        return "home_templets/about";
    }

    @GetMapping("/contact")
    public String viewContactPage() {
        return "home_templets/contact";
    }

    @GetMapping("/admin-login")
    public String viewAdminLoginPage() {
        return "home_templets/admin_login";
    }

    // Render client booking status checker page
    @GetMapping("/booking-status")
    public String viewBookingStatusPage(@RequestParam(value = "email", required = false) String email, Model model) {
        if (email != null && !email.trim().isEmpty()) {
            List<Event> myEvents = eventService.getEventsByAttendeeEmail(email.trim());
            model.addAttribute("myEvents", myEvents);
            model.addAttribute("queryEmail", email.trim());
        }
        return "home_templets/booking_status";
    }
}
