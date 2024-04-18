package org.example.servicereserveaccommodation.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Guest {
    @JsonProperty("name")
    private Name name;

    @JsonProperty("contact")
    private Contact contact;

    @Getter
    @Setter
    public static class Name {
        @JsonProperty("title")
        private String title;

        @JsonProperty("firstName")
        private String firstName;

        @JsonProperty("lastName")
        private String lastName;
    }

    @Getter
    @Setter
    public static class Contact {
        @JsonProperty("phone")
        private String phone;

        @JsonProperty("email")
        private String email;
    }
}
