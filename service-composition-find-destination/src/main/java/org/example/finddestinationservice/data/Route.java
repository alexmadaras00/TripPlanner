package org.example.finddestinationservice.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Route {
    @JsonProperty("summary")
    private String summary;

    @JsonProperty("leg")
    private Leg[] legs;
    @JsonProperty("copyrights")
    private String copyrights;

    @JsonProperty("overview_polyline")
    private Polyline overviewPolyline;
    @JsonProperty("bounds")
    private Bounds bounds;

    @Getter
    @Setter
    public static class Leg {
        @JsonProperty("steps")
        private Step[] steps;
    }

    @Getter
    @Setter
    public static class Step {
        @JsonProperty("travel_mode")
        private String travelMode;

        @JsonProperty("start_location")
        private Location startLocation;

        @JsonProperty("end_location")
        private Location endLocation;

        @JsonProperty("polyline")
        private Polyline polyline;

        @JsonProperty("duration")
        private Duration duration;

        @JsonProperty("distance")
        private Distance distance;

        @JsonProperty("start_address")
        private String startAddress;

        @JsonProperty("end_address")
        private String endAddress;
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
    public static class Polyline {
        @JsonProperty("points")
        private String points;
    }

    @Getter
    @Setter
    public static class Duration {
        @JsonProperty("value")
        private int value;
        @JsonProperty("text")
        private String text;
    }

    @Getter
    @Setter
    public static class Distance {
        @JsonProperty("value")
        private int value;
        @JsonProperty("text")
        private String text;
    }

    @Getter
    @Setter
    public static class Bounds {
        @JsonProperty("southwest")
        private Location southwest;
        @JsonProperty("northwest")
        private Location northeast;
    }
}

