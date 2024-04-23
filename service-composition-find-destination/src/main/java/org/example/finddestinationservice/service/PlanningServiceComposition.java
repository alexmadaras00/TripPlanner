package org.example.finddestinationservice.service;


import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Service
@RestController
@RequestMapping("/planner")
public class PlanningServiceComposition {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping
    public ResponseEntity<Void> orchestrateServices(@RequestBody String dataFromDestinationRecommender) {
        // Define the URL of the Route Planner Service
        String routePlannerServiceUrl = "http://localhost:8085/routes";
        System.out.println("JSON BODY: " +dataFromDestinationRecommender);
        // Create Http Headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        System.out.println("Planner");
        // Create Http Entity
      //  HttpEntity<JSONObject> entity = new HttpEntity<>(dataFromDestinationRecommender, headers);
        HttpEntity<String> entity = new HttpEntity<>(dataFromDestinationRecommender, headers);

        System.out.println("Entity: "+ entity);
        // Send POST request to Route Planner Service with the selected destination
        ResponseEntity<String> response = restTemplate.exchange(routePlannerServiceUrl, HttpMethod.POST, entity, String.class);
        String responseBody = response.getBody();

        System.out.println(responseBody);
        // Check response status code and handle accordingly
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Data sent successfully to Route Planner Service");
            URI redirectUrl = URI.create(routePlannerServiceUrl);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(redirectUrl);
            return new ResponseEntity<>(responseHeaders,HttpStatus.FOUND);  // Redirecting to Route Planner HTML page.
        } else {
            System.out.println("Failed to send data to Route Planner Service");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
