package org.example.servicereserveaccommodation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Booking {
    @JsonProperty("type")
    private String type;

    @JsonProperty("id")
    private String id;

    @JsonProperty("providerConfirmationId")
    private String providerConfirmationId;

    @JsonProperty("associatedRecords")
    private List<AssociatedRecord> associatedRecords;
}

