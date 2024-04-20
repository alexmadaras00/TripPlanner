package org.example.servicerouteplanner.controller;

import org.example.servicerouteplanner.domain.Route;
import org.example.servicerouteplanner.service.RoutePlannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoutePlannerController {
    @Autowired
    private RoutePlannerService routePlannerService;
    @GetMapping("/routes")
    public String showRoutes( ) {
//        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=";
//        String currentDestination = "Helsinki";
//        List<Route> routes = routePlannerService.getRoutes(currentDestination);
//        model.addAttribute("routes", routes);
        System.out.println("123x`");
        return "routes";
    }
}
