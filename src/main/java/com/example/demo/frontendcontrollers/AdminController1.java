package com.example.demo.frontendcontrollers;

import com.example.demo.entity.Event;
import com.example.demo.entity.Registration;
import com.example.demo.service.EventService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController1 {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Event> events = eventService.getAllEvents();
        
        // 1. Calculate Analytics Metrics
        int totalEvents = events.size();
        
        long pendingRequests = events.stream()
                .flatMap(e -> e.getRegistrations().stream())
                .filter(r -> "PENDING".equals(r.getStatus()))
                .count();
                
        long acceptedBookings = events.stream()
                .flatMap(e -> e.getRegistrations().stream())
                .filter(r -> "ACCEPTED".equals(r.getStatus()))
                .count();
                
        double totalRevenue = 0.0;
        for (Event event : events) {
            long acceptedCountForEvent = event.getRegistrations().stream()
                    .filter(r -> "ACCEPTED".equals(r.getStatus()))
                    .count();
            totalRevenue += (acceptedCountForEvent * event.getPrice());
        }
        
        // 2. Add metrics to model
        model.addAttribute("totalEvents", totalEvents);
        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("acceptedBookings", acceptedBookings);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("events", events);
        
        return "admin/admin_dashboard";
    }

    @GetMapping("/add-event")
    public String showAddEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/add-event";
    }

    @GetMapping("/edit-event/{id}")
    public String showEditEventForm(@PathVariable("id") String id, Model model) {
        Optional<Event> optionalEvent = eventService.getEventById(id);
        if (optionalEvent.isPresent()) {
            model.addAttribute("event", optionalEvent.get());
            return "admin/edit-event";
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/view-events")
    public String viewAllEvents(Model model) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        return "admin/view-events";
    }

    // View registered users directly from the embedded registrations list (NoSQL pattern)
    @GetMapping("/view-registrations/{id}")
    public String viewRegistrations(@PathVariable("id") String id, Model model) {
        Optional<Event> optionalEvent = eventService.getEventById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            model.addAttribute("event", event);
            model.addAttribute("registrations", event.getRegistrations());
            return "admin/view-registrations";
        }
        return "redirect:/admin/dashboard";
    }
}
