package org.example.servicedestinationrecommender.data;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("trips")
public class Trip {
    String homeLocation;
    String destination;
    String startDate;
    String endDate;
    int numberOfTravellers;
    String groupType;
    String motivation;
    String budget;

    public Trip(String homeLocation, String destination, int numberOfTravellers, String groupType, String motivation, String budget, String startDate, String endDate) {
        this.homeLocation = homeLocation;
        this.budget = budget;
        this.destination = destination;
        this.numberOfTravellers = numberOfTravellers;
        this.motivation = motivation;
        this.startDate = startDate;
        this.groupType = groupType;
        this.endDate = endDate;
    }
}