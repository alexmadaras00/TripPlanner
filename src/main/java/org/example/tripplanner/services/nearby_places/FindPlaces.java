package org.example.tripplanner.services.nearby_places;

import java.util.ArrayList;

public interface FindPlaces {
    ArrayList getNearbyPlaces(double latitude, double longitude);
}
