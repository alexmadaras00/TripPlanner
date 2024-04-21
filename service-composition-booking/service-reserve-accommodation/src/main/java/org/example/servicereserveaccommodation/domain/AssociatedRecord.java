package org.example.servicereserveaccommodation.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssociatedRecord {
    @JsonProperty("reference")
    private String reference;

    @JsonProperty("originSystemCode")
    private String originSystemCode;
}
