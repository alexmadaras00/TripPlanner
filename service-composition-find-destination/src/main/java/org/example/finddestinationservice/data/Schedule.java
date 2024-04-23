package org.example.finddestinationservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Schedule {
    @JsonProperty("name")
    private String name;
    @JsonProperty("numberDays")
    private int numberOfDays;
    @JsonProperty("days")
    private List<Day> days;

}
