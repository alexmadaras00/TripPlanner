package org.example.tripplanner.controller;

import org.example.tripplanner.services.booking.recommender.RecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TripPlannerController {
    @Autowired
    private RecommenderService recommenderService;

    @GetMapping("/")
    public String apiRoot() {
        return "index";
    }

    @PostMapping("/payments")
    public String showPayment(Model model) throws JSONException {
         model.addAttribute("payment", recommenderService.getCityCode("Paris"));
        return "show-payment-details";
    }
}
