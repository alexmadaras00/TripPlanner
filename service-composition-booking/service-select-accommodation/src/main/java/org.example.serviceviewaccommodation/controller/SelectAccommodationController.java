package org.example.serviceviewaccommodation.controller;


import org.example.serviceviewaccommodation.domain.HotelOfferResponse;
import org.example.serviceviewaccommodation.services.SelectAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SelectAccommodationController {
    @Autowired
    SelectAccommodationService selectAccommodationService;
    @GetMapping("/view-accommodation/{hotelID}")
    public String getSelectAccommodation(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable String hotelID, Model model
    ){
        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        try {
            HotelOfferResponse offer = selectAccommodationService.receiveOffer(hotelID);
            model.addAttribute("offers", offer.getData());
        } catch (Exception e) {
            return "error";
        }
        return "show-property";
    }
    @RequestMapping  ("/book/{offerID}")
    public String getList(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable String offerID){
        System.out.println(offerID);
        System.out.println("Here4" + endDate);
        String addition = "?source=" + source + "&destination=" + destination + "&numberOfTravelers=" + numberOfTravelers + "&motivation=" + motivation + "&groupType=" + groupType + "&startDate=" + startDate + "&endDate=" + endDate + "&budgetType=" + budgetType;

        return "redirect:http://localhost:8082/book/"+offerID+addition;
    }

}
