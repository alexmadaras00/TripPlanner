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
    public String getSelectAccommodation(@PathVariable String hotelID, Model model){
        HotelOfferResponse offer = selectAccommodationService.receiveOffer(hotelID);
        model.addAttribute("offers", offer.getData());
        return "show-property";
    }
    @RequestMapping  ("/book/{offerID}")
    public String getList(@PathVariable String offerID){
        System.out.println(offerID);
        return "redirect:http://localhost:8082/book/"+offerID;
    }

}
