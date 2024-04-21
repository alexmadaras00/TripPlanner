package org.example.servicescheduleactivities.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import org.example.servicescheduleactivities.data.enums.Budget;
import org.example.servicescheduleactivities.data.enums.GroupType;
import org.example.servicescheduleactivities.data.enums.Motivation;
import org.springframework.data.annotation.Id;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class TripForm {
    @Id
    @JsonProperty("id")
    private String id;
    @JsonProperty("home")
    private String homeLocation;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("numberOfTravellers")
    private int numberOfTravellers;
    @JsonProperty("groupType")
    private GroupType groupType;
    @JsonProperty("budget")
    private Budget budget;
    @JsonProperty("motivation")
    private Motivation motivation;
    @JsonProperty("reviews")
    private List<Review> reviewList;
}
