package org.example.tripplanner.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TripPlannerController {

    @PostMapping("/")
    public String apiRoot(){
        return "Welcome to Trip Planner";
    }
}
