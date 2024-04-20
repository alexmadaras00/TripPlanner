package org.example.servicedestinationrecommender.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Destination {
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;

    public Destination(String city, String country) {
        this.city = city;
        this.country = country;
    }
}
