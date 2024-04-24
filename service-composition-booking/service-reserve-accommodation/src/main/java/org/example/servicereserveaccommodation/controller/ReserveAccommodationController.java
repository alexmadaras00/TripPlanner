package org.example.servicereserveaccommodation.controller;

import com.amadeus.booking.HotelBookings;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelBooking;
import org.example.servicereserveaccommodation.data.BookingForm;
import org.example.servicereserveaccommodation.data.Data;
import org.example.servicereserveaccommodation.data.Guest;
import org.example.servicereserveaccommodation.domain.BookingData;
import org.example.servicereserveaccommodation.service.ReserveAccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReserveAccommodationController {
    @Autowired
    private ReserveAccommodationService reserveAccommodationService;

    @GetMapping("/book/{offerID}")
    public String bookTheAccommodation(
            @RequestParam("source") String source,
            @RequestParam("destination") String destination,
            @RequestParam("numberOfTravelers") String numberOfTravelers,
            @RequestParam("motivation") String motivation,
            @RequestParam("groupType") String groupType,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("budgetType") String budgetType,
            @PathVariable String offerID, Model model) {
        BookingForm bookingForm = new BookingForm();
        System.out.println("OFFER ID: " + offerID);
        Data data = new Data();
        data.setOfferId(offerID);
        data.setGuests(List.of(new Guest()));
        data.setPayments(List.of(new Data.Payment()));
        bookingForm.setData(data);
        model.addAttribute("bookingForm", bookingForm);
        model.addAttribute("offerID", offerID);
        return "book";
    }


    @PostMapping("/confirmation/{offerID}")
    public String getConfirmation(@ModelAttribute BookingForm bookingForm, @PathVariable String offerID, Model model) throws ResponseException {
        BookingData bookingData = reserveAccommodationService.bookHotel(bookingForm);
        System.out.println("booking data: "+ bookingData.getData());
        if (bookingData.getData().get(0).getProviderConfirmationId()!=null) {
            model.addAttribute("hotelBookings", bookingData);
            return "confirmation";
        } else {
            String error = bookingData.getData().get(0).getType();
            model.addAttribute("error", error);
            model.addAttribute("offerID", offerID);
            System.out.println("Heere:" + offerID);
            System.out.println(bookingData.getData().get(0).getType());
            bookingData.setData(new ArrayList<>());
            return "redirect:/error-booking/" + offerID;
        }
    }

    @RequestMapping("/review")
    public String goToReview() {
        return "redirect:http://localhost:8082/review";
    }

    @GetMapping("/error-booking/{offerID}")
    public String getErrorPage(@PathVariable String offerID, Model model) {
        model.addAttribute("offerID", offerID);
        return "error";
    }

    @RequestMapping("/book/{offerID}")
    public String getList(@PathVariable String offerID) {
        System.out.println(offerID);
        return "redirect:http://localhost:8082/book/" + offerID;
    }
}
