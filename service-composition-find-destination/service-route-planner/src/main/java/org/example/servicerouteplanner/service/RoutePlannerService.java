package org.example.servicerouteplanner.service;

import org.example.servicerouteplanner.domain.Route;

import java.util.List;

public interface RoutePlannerService {
    List<Route> getRoutes(String destination);
}
