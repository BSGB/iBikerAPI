package com.ibiker.ibiker.Interfaces;

import com.ibiker.ibiker.Models.Route;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IRouteService {
    Route postRoute(Route route) throws IOException;
    Route getRouteById(String routeId) throws FileNotFoundException;
    Route putRoute(Route route);
    boolean deleteRoute(String routeId);
    List<Route> getAllUserRoutes(String issuer);
    List<Route> getAllRoutes();
}
