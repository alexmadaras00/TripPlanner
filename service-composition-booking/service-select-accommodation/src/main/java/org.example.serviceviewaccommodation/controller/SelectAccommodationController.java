package org.example.serviceviewaccommodation.controller;


import org.example.serviceviewaccommodation.domain.HotelOfferResponse;
import org.example.serviceviewaccommodation.services.SelectAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SelectAccommodationController {
    @Autowired
    SelectAccommodationService selectAccommodationService;
    @GetMapping("/selectAccommodation")
    public String getSelectAccommodation(Model model){
        String hotelId = "MCLONGHM";
        HotelOfferResponse offer = selectAccommodationService.receiveOffer(hotelId);
        model.addAttribute("offers", offer.getData());
        return "show-property";
    }
    @PostMapping("/selectAccommodation")
    public String getList(Model model){
        String hotelId = "MCLONGHM";
        HotelOfferResponse offer = selectAccommodationService.receiveOffer(hotelId);
        model.addAttribute("offers", offer.getData());
        return "show-property";
    }
}
