package org.example.tripplanner.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class CoordinatesResponse {
    @JsonProperty("results")
    private List<Result> results;
    @JsonProperty("status")
    private String status;

    @Getter
    @Setter
    public static class Result {
        @JsonProperty("address_components")
        private List<AddressComponent> addressComponents;
        @JsonProperty("formatted_address")
        private String formattedAddress;
        @JsonProperty("geometry")
        private Geometry geometry;
        @JsonProperty("place_id")
        private String placeId;
        @JsonProperty("types")
        private List<String> types;

        @Getter
        @Setter
        public static class AddressComponent {
            @JsonProperty("long_name")
            private String longName;
            @JsonProperty("short_name")
            private String shortName;
            @JsonProperty("types")
            private List<String> types;
        }

        @Getter
        @Setter
        public static class Geometry {
            @JsonProperty("bounds")
            private Bounds bounds;
            @JsonProperty("location")
            private Location location;
            @JsonProperty("location_type")
            private String locationType;
            @JsonProperty("viewport")
            private Viewport viewport;

            @Getter
            @Setter
            public static class Bounds {
                @JsonProperty("northeast")
                private Northeast northeast;
                @JsonProperty("southwest")
                private Southwest southwest;

                @Getter
                @Setter
                public static class Northeast {
                    @JsonProperty("lat")
                    private double lat;
                    @JsonProperty("lng")
                    private double lng;
                }

                @Getter
                @Setter
                public static class Southwest {
                    @JsonProperty("lat")
                    private double lat;
                    @JsonProperty("lng")
                    private double lng;
                }
            }

            @Getter
            @Setter
            public static class Location {
                @JsonProperty("lat")
                private double lat;
                @JsonProperty("lng")
                private double lng;
            }

            @Getter
            @Setter
            public static class Viewport {
                @JsonProperty("northeast")
                private Bounds.Northeast northeast;
                @JsonProperty("southwest")
                private Bounds.Southwest southwest;
            }
        }
    }
}

