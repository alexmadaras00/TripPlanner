package org.example.finddestinationservice.service;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;


@Service
@RestController
public class PlanningServiceComposition {

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping("/planner")
    public void callRoutesPlanner(@RequestParam("source") String source,
                                    @RequestParam("destination") String destination,@RequestParam("budgetType") String budgetType, HttpServletResponse httpServletResponse) {
        // Define the URL of the Route Planner Service
        String redirectUrl = "http://localhost:8085/routes?source="+source+"&destination="+destination;
        try {
            httpServletResponse.sendRedirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
