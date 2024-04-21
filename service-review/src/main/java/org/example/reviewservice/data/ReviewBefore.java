package org.example.reviewservice.data;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@Table("review_before")
public class ReviewBefore {
    @Id
    String id;
    int booking_id;
    int trip_id;
    int destination_rating;
    int route_rating;
    int schedule_rating;
    int booking_rating;

    public ReviewBefore(int booking_id, int trip_id, int destination_rating, int route_rating, int schedule_rating, int booking_rating) {
        this.booking_id = booking_id;
        this.trip_id = trip_id;
        this.destination_rating = destination_rating;
        this.route_rating = route_rating;
        this.schedule_rating = schedule_rating;
        this.booking_rating = booking_rating;
    }
}
