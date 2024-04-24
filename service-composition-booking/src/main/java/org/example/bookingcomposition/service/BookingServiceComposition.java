package org.example.bookingcomposition.service;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class BookingServiceComposition {
    String serviceRecommendAccommodationUrl = "http://localhost:8083/recommend-accommodation";
    String serviceViewAccommodationOffersUrl = "http://localhost:8084/view-accommodation";
    String serviceBookAccommodationUrl = "http://localhost:8085/book";

    @GetMapping("/recommend-accommodation")
    public String recommendAccommodation(Model model) {

        model.addAttribute("serviceUrl", serviceRecommendAccommodationUrl);
        return "booking";
    }

    @GetMapping("/view-accommodation/{hotelID}")
    public String viewAccommodation(@PathVariable String hotelID, Model model) {
        model.addAttribute("serviceUrl", serviceViewAccommodationOffersUrl+"/"+hotelID);
        model.addAttribute("hotelID", hotelID);
        System.out.println("Composition ID: "+hotelID);
        return "booking";
    }

    @GetMapping("/book/{offerID}")
    public String bookAccommodation(@PathVariable String offerID, Model model) {
        model.addAttribute("serviceUrl", serviceBookAccommodationUrl+"/"+offerID);
        System.out.println("composiotion offerID: "+offerID);
        return "booking";
    }
}

