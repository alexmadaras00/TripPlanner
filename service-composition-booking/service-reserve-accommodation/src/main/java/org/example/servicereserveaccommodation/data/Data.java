package org.example.servicereserveaccommodation.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Data {
    @JsonProperty("offerId")
    private String offerId;

    @JsonProperty("guests")
    private List<Guest> guests;

    @JsonProperty("payments")
    private List<Payment> payments;

    @Getter
    @Setter
    public static class Payment {
        @JsonProperty("method")
        private String method;

        @JsonProperty("card")
        private Card card;

        @Getter
        @Setter
        public static class Card {
            @JsonProperty("vendorCode")
            private String vendorCode;

            @JsonProperty("cardNumber")
            private String cardNumber;

            @JsonProperty("expiryDate")
            private String expiryDate;
        }
    }
}
