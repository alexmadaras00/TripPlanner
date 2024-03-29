package org.example.tripplanner.data.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.tripplanner.data.booking.enums.Unit;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Hotel {

    @JsonProperty("chainCode")
    private String chainCode;

    @JsonProperty("iataCode")
    private String iataCode;

    @JsonProperty("dupeId")
    private int dupeId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hotelId")
    private String hotelId;

    @JsonProperty("geoCode")
    private GeoCode geoCode;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("distance")
    private Distance distance;

    @Getter
    @Setter
    public static class GeoCode {

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;

    }
    @Getter
    @Setter
    private static class Address {
        @JsonProperty("description")
        private String description;
        @JsonProperty("countryCode")
        private String countryCode;

    }
    @Getter
    @Setter
    private static class Distance {
        @JsonProperty("description")
        private String description;
        @JsonProperty("unit")
        private Unit unit;
        @JsonProperty("displayValue")
        private String displayValue;
        @JsonProperty("isUnlimited")
        private String unlimited;
    }
}

