package org.example.tripplanner.data.booking;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripForm {
    private String city;
    private int adults;
    private String checkin;
    private String checkout;
}
