package org.example.servicerouteplanner.controller;

import com.google.maps.errors.ApiException;
import org.example.servicerouteplanner.domain.Route;
import org.example.servicerouteplanner.service.RoutePlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class RoutePlannerController {
    @Value("${google.directionsAPI.key}")
    private String googleMapsApiKey;
    @Autowired
    private RoutePlannerService routePlannerService;

    @GetMapping("/routes")
    public String loadRoutes(@RequestParam("source") String source,
                             @RequestParam("destination") String destination, Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        try {
            List<Route> routes = routePlannerService.getRoutes(source, destination);
            model.addAttribute("source", source);
            model.addAttribute("destination", destination);
            model.addAttribute("routes", routes);
        } catch (IOException | InterruptedException | ApiException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return "error";
        }
        return "routes";
    }


}
