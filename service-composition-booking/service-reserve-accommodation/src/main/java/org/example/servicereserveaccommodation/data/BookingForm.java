package org.example.servicereserveaccommodation.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BookingForm {
    @JsonProperty("data")
    private Data data;
}


