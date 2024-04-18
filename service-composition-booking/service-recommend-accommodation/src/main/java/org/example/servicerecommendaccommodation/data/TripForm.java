package org.example.servicerecommendaccommodation.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;


@RedisHash("Trip")
@Getter
@Setter
public class TripForm {
    @Id
    private String id;
    private String city;
    private int adults;
    private String checkin;
    private String checkout;
}
