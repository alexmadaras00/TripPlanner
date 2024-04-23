package org.example.servicedestinationrecommender.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.servicedestinationrecommender.data.TripForm;
import org.example.servicedestinationrecommender.domain.Destination;
import org.example.servicedestinationrecommender.service.DestinationRecommenderService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
    public String goToPlannerService(@ModelAttribute("tripForm") TripForm tripForm, @PathVariable(required = false) String city, @RequestParam String country) throws JsonProcessingException {
        String planningServiceCompositionUrl = "http://localhost:8890/planner";
        System.out.println("Redirect");

        System.out.println(city);
        System.out.println(country);
        Destination selectedDestination = new Destination(city, country);
        logger.info("Redirect");
        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();
        // Create Http Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        JSONObject dataObject = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        String tripFormJson = mapper.writeValueAsString(tripForm);
        JSONObject tripFormJsonObject = new JSONObject(tripFormJson);
        String selectedDestinationJson = mapper.writeValueAsString(selectedDestination);
        JSONObject destinationJsonObject = new JSONObject(selectedDestinationJson);

        dataObject.put("selectedDestination", destinationJsonObject);
        dataObject.put("tripForm", tripFormJsonObject);  // This line is added
        // Create Http Entity
        System.out.println(dataObject.get("tripForm").toString());
        System.out.println(dataObject.get("selectedDestination").toString());
        // Send POST request to Planning Service Composition with the recommendations
        System.out.println("URL: " + planningServiceCompositionUrl);
        System.out.println("Headers: " + headers);

        ResponseEntity<String> response = null;
        String responseBody = "";
        try {
            HttpEntity<String> request = new HttpEntity<>(dataObject.toString(), headers);
            System.out.println("Request:" +request);
            response = restTemplate.postForEntity(planningServiceCompositionUrl,request,String.class);
            responseBody = response.getBody();
            System.out.println("Body: "+ responseBody);
        } catch (RestClientException e) {
            e.printStackTrace();
            System.out.println(e.getStackTrace().toString());
            return "error";
        }


        logger.info("Response: {}", responseBody);
        // Check response status code and handle accordingly
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("success");
            return "redirect:http://localhost:8085/route";
        } else {
            System.out.println("Failed to send recommendations to Planning Service Composition");
            return "error";
        }

    }



}
