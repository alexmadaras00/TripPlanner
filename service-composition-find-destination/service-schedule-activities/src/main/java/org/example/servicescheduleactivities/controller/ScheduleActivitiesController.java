package org.example.servicescheduleactivities.controller;

import lombok.Getter;
import org.example.servicescheduleactivities.data.TripForm;

import org.example.servicescheduleactivities.domain.Day;
import org.example.servicescheduleactivities.service.ScheduleActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.List;

@Controller
public class ScheduleActivitiesController {
    @Autowired
    private ScheduleActivitiesService scheduleActivitiesService;
    @GetMapping("/schedule")
    public String getForm(@ModelAttribute TripForm tripForm, Model model) throws JSONException, IOException {
        model.addAttribute("tripForm",tripForm);
        String destination = "Warsaw";
        List<Day> schedulePlan = scheduleActivitiesService.getActivities(tripForm, destination);
        model.addAttribute("schedule", schedulePlan);
        return "schedule-activities";
    }

    @PostMapping("/schedule")
    public String getSchedule(Model model) throws IOException, JSONException {
        TripForm tripForm = new TripForm();
        String destination = "Warsaw";
        List<Day> schedulePlan = scheduleActivitiesService.getActivities(tripForm, destination);
        model.addAttribute("destination", destination);
        model.addAttribute("schedule", schedulePlan);
        return "schedule-activities";
    }

    @GetMapping("/error-schedule")
    public String getErrorPage() {
        return "error-schedule";
    }
}
