package org.example.finddestinationservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Day {
    @JsonProperty("number")
    private int number;
    @JsonProperty("activities")
    private List<Activity> activityList;

}
