package org.example.tripplanner.data.booking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class HotelOfferResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("hotel")
    private Hotel hotel;

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("offers")
    private ArrayList<Offer> offers;

    @JsonProperty("self")
    private String self;

    @Getter
    @Setter
    public static class Hotel {

        @JsonProperty("type")
        private String type;

        @JsonProperty("hotelId")
        private String hotelId;

        @JsonProperty("chainCode")
        private String chainCode;

        @JsonProperty("dupeId")
        private String dupeId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("cityCode")
        private String cityCode;

        @JsonProperty("latitude")
        private double latitude;

        @JsonProperty("longitude")
        private double longitude;
    }

    @Getter
    @Setter
    public static class Offer {

        @JsonProperty("id")
        private String id;

        @JsonProperty("checkInDate")
        private String checkInDate;

        @JsonProperty("checkOutDate")
        private String checkOutDate;

        @JsonProperty("rateCode")
        private String rateCode;

        @JsonProperty("rateFamilyEstimated")
        private Map<String, String> rateFamilyEstimated;

        @JsonProperty("room")
        private Room room;

        @JsonProperty("guests")
        private Map<String, Integer> guests;

        @JsonProperty("price")
        private Price price;

        @JsonProperty("policies")
        private Policies policies;

        @JsonProperty("self")
        private String self;

        @Getter
        @Setter
        public static class Room {

            @JsonProperty("type")
            private String type;

            @JsonProperty("typeEstimated")
            private Map<String, Object> typeEstimated;

            @JsonProperty("description")
            private Map<String, String> description;
        }

        @Getter
        @Setter
        public static class Price {

            @JsonProperty("currency")
            private String currency;

            @JsonProperty("base")
            private String base;

            @JsonProperty("total")
            private String total;

            @JsonProperty("variations")
            private Map<String, Object> variations;
        }

        @Getter
        @Setter
        public static class Policies {

            @JsonProperty("paymentType")
            private String paymentType;

            @JsonProperty("cancellation")
            private Map<String, String> cancellation;
        }
    }
}
