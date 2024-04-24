package org.example.servicerecommendaccommodation.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.example.servicerecommendaccommodation.data.Hotel;
import org.example.servicerecommendaccommodation.data.TripForm;
import org.example.servicerecommendaccommodation.service.RecommenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecommenderAccommodationController {
    @Autowired
    private RecommenderService recommenderService;
    @GetMapping("/recommend-accommodation")
    public ModelAndView showInit(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            Model model, HttpServletRequest request) {
        String cityCode = recommenderService.getCityCode(destination.split(",")[0]);
        ArrayList<Hotel> hotels = recommenderService.searchHotelsByCityCode(cityCode);
        model.addAttribute("recommendationList", hotels);
        request.getSession().setAttribute("city", destination.split(",")[0]);
        System.out.println("List: "+recommenderService.searchHotelsByCityCode(cityCode));

        model.addAttribute("source", source);
        model.addAttribute("destination", destination);
        model.addAttribute("numberOfTravelers", numberOfTravelers);
        model.addAttribute("motivation", motivation);
        model.addAttribute("groupType", groupType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("budgetType", budgetType);
        return new ModelAndView("show-recommendations", model.asMap());
    }

    @RequestMapping("/view-accommodation/{hotelID}")
    public String performAction(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable(required = false) String hotelID ) {
        System.out.println("id: "+hotelID);
        String addition = "?source=" + source + "&destination=" + destination + "&numberOfTravelers=" + numberOfTravelers + "&motivation=" + motivation + "&groupType=" + groupType + "&startDate=" + startDate + "&endDate=" + endDate + "&budgetType=" + budgetType;

        return "redirect:http://localhost:8083/view-accommodation/"+hotelID+addition;
    }
}
