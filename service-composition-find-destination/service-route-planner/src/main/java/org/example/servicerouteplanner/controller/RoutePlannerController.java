package org.example.servicerouteplanner.controller;

import com.google.maps.errors.ApiException;
import org.example.servicerouteplanner.domain.Route;
import org.example.servicerouteplanner.service.RoutePlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String showRoutes(
            Model model,
            @RequestParam("source") String source,
            @RequestParam("destination") String destination)
    throws IOException, InterruptedException, ApiException {
        List<Route> routes = routePlannerService.getRoutes(source, destination);
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        model.addAttribute("routes", routes);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        return "routes";
    }
}
