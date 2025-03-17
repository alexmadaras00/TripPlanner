package org.example.finddestinationservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.finddestinationservice.data.enums.Budget;
import org.example.finddestinationservice.data.enums.GroupType;
import org.example.finddestinationservice.data.enums.Motivation;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Table("trips")
public class TripForm {
    @Id
    @JsonProperty("id")
    private String tripid;
    @JsonProperty("user_id")
    private String userid;
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
    @JsonProperty("transportation_mean")
    private String transportatonMean;

}
