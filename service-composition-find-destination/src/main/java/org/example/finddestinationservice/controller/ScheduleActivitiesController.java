package org.example.finddestinationservice.controller;

import com.google.maps.errors.ApiException;
import lombok.Getter;
import org.example.finddestinationservice.data.Day;
import org.example.finddestinationservice.data.Route;
import org.example.finddestinationservice.data.TripForm;
import org.example.finddestinationservice.service.RoutePlannerService;
import org.example.finddestinationservice.service.ScheduleActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class ScheduleActivitiesController {
    @Value("${google.directionsAPI.key}")
    private String googleMapsApiKey;
    @Autowired
    private ScheduleActivitiesService scheduleActivitiesService;
    @Autowired
    private RoutePlannerService routePlannerService;
    @GetMapping("/schedule")
    public String getSchedule(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            Model model
    ) throws IOException, JSONException {
        TripForm tripForm = new TripForm();
        List<Day> schedulePlan = scheduleActivitiesService.getActivities(tripForm, destination);
        model.addAttribute("destination", destination);
        model.addAttribute("schedule", schedulePlan);
        model.addAttribute("source", source);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        return "schedule-activities";
    }

    @GetMapping("/routes")
    public String loadRoutes(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            Model model
    ) {
        System.out.println("reached");
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        try {
            List<Route> routes = routePlannerService.getRoutes(source, destination);
            model.addAttribute("source", source);
            model.addAttribute("destination", destination);
            model.addAttribute("routes", routes);
            model.addAttribute("numberOfTravelers", numberOfTravelers);
            model.addAttribute("motivation", motivation);
            model.addAttribute("groupType", groupType);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("budgetType", budgetType);
        } catch (IOException | InterruptedException | ApiException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return "error";
        }
        return "routes";
    }
}
