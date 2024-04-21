package org.example.servicerouteplanner.service;

import com.google.maps.errors.ApiException;
import org.example.servicerouteplanner.domain.Route;

import java.io.IOException;
import java.util.List;

public interface RoutePlannerService {
    List<Route> getRoutes(String source, String destination) throws IOException, InterruptedException, ApiException;
}
