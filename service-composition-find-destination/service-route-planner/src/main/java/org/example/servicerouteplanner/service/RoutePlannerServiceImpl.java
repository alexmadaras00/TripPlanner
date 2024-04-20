package org.example.servicerouteplanner.service;

import org.example.servicerouteplanner.domain.Route;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutePlannerServiceImpl implements RoutePlannerService {
    @Override
    public List<Route> getRoutes(String destination) {
        return null;
    }
}
