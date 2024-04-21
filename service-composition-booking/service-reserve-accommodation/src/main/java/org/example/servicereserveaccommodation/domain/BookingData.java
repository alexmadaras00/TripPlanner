package org.example.servicereserveaccommodation.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookingData {
    @JsonProperty("data")
    private List<Booking> data;
}


