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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReserveAccommodationController {
    @Autowired
    private ReserveAccommodationService reserveAccommodationService;

    @GetMapping("/book")
    public String bookTheAccommodation(Model model) {
        BookingForm bookingForm = new BookingForm();
        Data data = new Data();
        data.setOfferId("NRPQNQBOJM");
        data.setGuests(List.of(new Guest()));
        data.setPayments(List.of(new Data.Payment()));
        bookingForm.setData(data);
        model.addAttribute("bookingForm", bookingForm);
        return "book";
    }


    @PostMapping("/confirmation")
    public String getConfirmation(@ModelAttribute BookingForm bookingForm, Model model) throws ResponseException {
        bookingForm.getData().setOfferId("NRPQNQBOJM");
        BookingData bookingData = reserveAccommodationService.bookHotel(bookingForm);
        if (bookingData.getData().get(0).getAssociatedRecords()!= null) {
            model.addAttribute("hotelBookings", bookingData);
            return "confirmation";
        } else {
            String error = bookingData.getData().get(0).getType();
            model.addAttribute("error", error);
            System.out.println(bookingData.getData().get(0).getType());
            bookingData.setData(new ArrayList<>());
            return "error";
        }
    }

    @GetMapping("/error-booking")
    public String getErrorPage() {
        return "error";
    }
}
