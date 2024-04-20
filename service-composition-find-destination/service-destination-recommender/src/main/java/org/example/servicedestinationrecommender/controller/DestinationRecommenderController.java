package org.example.servicedestinationrecommender.controller;

import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.example.servicedestinationrecommender.service.DestinationRecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@Controller
public class DestinationRecommenderController {
    @Autowired
    private DestinationRecommenderService destinationRecommenderService;

    public DestinationRecommenderController(DestinationRecommenderService destinationRecommenderService) {
    }
    @GetMapping("/recommendation-list")
    public String getPageForm(@ModelAttribute TripForm tripForm, Model model){
        model.addAttribute("tripForm",tripForm);
        return "home";
    }
    @PostMapping("/recommendation-list")
    public String getRecommendations(@ModelAttribute TripForm tripForm, Model model) throws IOException, JSONException {
        List<Destination> destinationList = destinationRecommenderService.getRecommendations(tripForm);

        model.addAttribute("destinations",destinationList);
        return "home";
    }
}
