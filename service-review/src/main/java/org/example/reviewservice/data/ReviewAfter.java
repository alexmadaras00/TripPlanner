package org.example.reviewservice.data;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("review_after")
public class ReviewAfter {
    @Id
    String id;
    int booking_id;
    int trip_id;
    int arrived_rating;
    int accommodation_rating;
    int offer_rating;
    int schedule_rating;
    int overall_experience_rating;

    public ReviewAfter(int booking_id, int trip_id, int arrived_rating, int accommodation_rating, int offer_rating, int schedule_rating, int overall_experience_rating) {
        this.booking_id = booking_id;
        this.trip_id = trip_id;
        this.arrived_rating = arrived_rating;
        this.accommodation_rating = accommodation_rating;
        this.offer_rating = offer_rating;
        this.schedule_rating = schedule_rating;
        this.overall_experience_rating = overall_experience_rating;
    }
}
