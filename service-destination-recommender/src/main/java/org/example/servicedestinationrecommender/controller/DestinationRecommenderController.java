package org.example.servicedestinationrecommender.controller;


import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.example.servicedestinationrecommender.service.DestinationRecommenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.example.servicedestinationrecommender.data.Trip;
import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes("tripForm")
public class DestinationRecommenderController {
    @Autowired
    private DestinationRecommenderService destinationRecommenderService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DestinationRecommenderController(DestinationRecommenderService destinationRecommenderService) {
    }

    @ModelAttribute("selectedDestination")
    public Destination selectedDestination() {
        return new Destination();
    }

    @ModelAttribute("tripForm")
    public TripForm tripForm() {
        return new TripForm();
    }


    @GetMapping("/recommendation-list")
    public String getPageForm(Model model) {
        if (!model.containsAttribute("tripForm")) {
            model.addAttribute("tripForm", new TripForm());
        }
        return "home";
    }

    @PostMapping("/recommendation-list")
    public String getRecommendations(@ModelAttribute("tripForm") TripForm tripForm, Model model) throws IOException {
        List<Destination> destinationList = destinationRecommenderService.getRecommendations(tripForm);
        System.out.println("GOOOd");
        System.out.println(tripForm);
        System.out.println(tripForm.getNumberOfTravellers());
        model.addAttribute("destinations", destinationList);
        return "home";
    }

    @PostMapping("/planner/{city:^.*$}")
    public String goToPlannerService(@ModelAttribute("tripForm") TripForm tripForm, @PathVariable(required = false) String city, @RequestParam String country) {
        Trip t = new Trip(tripForm.getHomeLocation(), city + "," + country, tripForm.getNumberOfTravellers(), tripForm.getGroupType().toString(), tripForm.getMotivation().toString(), tripForm.getBudget().toString(), tripForm.getStartDate(), tripForm.getEndDate());
        destinationRecommenderService.saveTrip(t);
        return "redirect:http://localhost:8082/routes?source=" + tripForm.getHomeLocation() + "&destination=" + city + "," + country+"&numberOfTravelers="+tripForm.getNumberOfTravellers()+"&motivation="+ tripForm.getMotivation()+"&groupType="+ tripForm.getGroupType()+"&startDate="+ tripForm.getStartDate()+"&endDate="+ tripForm.getEndDate()+"&budgetType="+ tripForm.getBudget();
    }


}
