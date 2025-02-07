package org.example.servicedestinationrecommender.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.servicedestinationrecommender.data.Trip;
import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.example.servicedestinationrecommender.service.DestinationRecommenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Controller
@SessionAttributes({"tripForm", "user_id"})
public class DestinationRecommenderController {
    @Autowired
    private DestinationRecommenderService destinationRecommenderService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${edenAPI.key}")
    private String secretKey;

    public DestinationRecommenderController(DestinationRecommenderService destinationRecommenderService, RestTemplate restTemplate) {
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
//        if(restTemplate.getForObject("http://localhost:8087/rating?column=destinations", String.class)!=null) {
//            String responseData = restTemplate.getForObject("http://localhost:8087/rating?column=destinations", String.class);
//            model.addAttribute("rating", responseData);
//        }

        return "home";
    }

    @PostMapping("/recommendation-list")
    public String getRecommendations(@ModelAttribute("tripForm") TripForm tripForm, @RequestParam("jwt") String jwt, Model model) throws IOException {
        if (jwt == null || jwt.equals("${jwt}")) {
            logger.error("Invalid JWT received: {}", jwt);
            throw new RuntimeException("Invalid JWT received");
        }
        int userId = getUserIdFromJwt(jwt); // Extract user_id from JWT
        model.addAttribute("user_id", userId);
        List<Destination> destinationList = destinationRecommenderService.getRecommendations(tripForm);
        destinationRecommenderService.saveTrip(tripForm);
        System.out.println(tripForm);
        System.out.println(tripForm.getNumberOfTravellers());
        model.addAttribute("destinations", destinationList);
        System.out.println("Destinations added");
        return "home";
    }

    @PostMapping("/planner/{city:^.*$}")
    public String goToPlannerService(@ModelAttribute("tripForm") TripForm tripForm, @ModelAttribute("user_id") int userId, @PathVariable(required = false) String city, @RequestParam String country) {
        Trip t = new Trip(tripForm.getHomeLocation(), city + "," + country, tripForm.getNumberOfTravellers(), tripForm.getGroupType().toString(), tripForm.getMotivation().toString(), tripForm.getBudget().toString(), tripForm.getStartDate(), tripForm.getEndDate());
        destinationRecommenderService.saveTrip(tripForm);
        tripForm.setUserid(String.valueOf(userId));
        System.out.println("Selected destination");
        return "redirect:http://localhost:8082/routes?source=" + tripForm.getHomeLocation() + "&destination=" + city + "," + country+"&numberOfTravelers="+tripForm.getNumberOfTravellers()+"&motivation="+ tripForm.getMotivation()+"&groupType="+ tripForm.getGroupType()+"&startDate="+ tripForm.getStartDate()+"&endDate="+ tripForm.getEndDate()+"&budgetType="+ tripForm.getBudget();
    }
    private int getUserIdFromJwt(String jwt) {
        System.out.println("JWT key: "+jwt);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println("User ID: "+ claims.get("user_id"));
        return (int) claims.get("user_id");
    }

}
