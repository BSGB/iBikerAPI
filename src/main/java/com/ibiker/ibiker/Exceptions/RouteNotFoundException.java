package com.ibiker.ibiker.Exceptions;

public class RouteNotFoundException extends RuntimeException {
    public RouteNotFoundException(){
        super("No route found.");
    }
}
