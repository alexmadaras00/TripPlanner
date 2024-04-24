package org.example.finddestinationservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    @JsonProperty("name")
    private String name;
    @JsonProperty("startTime")
    private String startTime;
}
