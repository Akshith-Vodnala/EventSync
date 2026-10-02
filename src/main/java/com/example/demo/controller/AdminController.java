package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/api")
public class AdminController {

    @Autowired
    private EventService eventService;

    @PostMapping("/save-event")
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventService.createEvent(event);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update-event/{id}")
    public String updateEvent(@PathVariable("id") String id, @ModelAttribute("event") Event event) {
        event.setId(id);
        eventService.createEvent(event);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable("id") String id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/dashboard";
    }

    // Accept a pending booking request
    @GetMapping("/accept-booking")
    public String acceptBooking(@RequestParam("eventId") String eventId, @RequestParam("userId") String userId) {
        eventService.acceptBooking(eventId, userId);
        return "redirect:/admin/view-registrations/" + eventId;
    }

    // Reject an accepted or pending booking request
    @GetMapping("/reject-booking")
    public String rejectBooking(@RequestParam("eventId") String eventId, @RequestParam("userId") String userId) {
        eventService.rejectBooking(eventId, userId);
        return "redirect:/admin/view-registrations/" + eventId;
    }
}
