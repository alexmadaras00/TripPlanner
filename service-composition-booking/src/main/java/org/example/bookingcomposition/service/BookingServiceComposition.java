package org.example.bookingcomposition.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BookingServiceComposition {
    String serviceRecommendAccommodationUrl = "http://localhost:8084/recommend-accommodation";
    String serviceViewAccommodationOffersUrl = "http://localhost:8086/view-accommodation";
    String serviceBookAccommodationUrl = "http://localhost:8085/book";

    @GetMapping("/recommend-accommodation")
    public String recommendAccommodation(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            Model model) {
        model.addAttribute("serviceUrl", serviceRecommendAccommodationUrl);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        return "booking";
    }

    @GetMapping("/view-accommodation/{hotelID}")
    public String viewAccommodation(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable String hotelID, Model model) {
        model.addAttribute("serviceUrl", serviceViewAccommodationOffersUrl+"/"+hotelID);
        model.addAttribute("hotelID", hotelID);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        System.out.println("Here3" + source);
        System.out.println("Composition ID: "+hotelID);
        return "booking";
    }

    @GetMapping("/book/{offerID}")
    public String bookAccommodation(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable String offerID, Model model) {
        model.addAttribute("serviceUrl", serviceBookAccommodationUrl+"/"+offerID);
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        System.out.println("composition offerID: "+offerID);
        return "booking";
    }
}

