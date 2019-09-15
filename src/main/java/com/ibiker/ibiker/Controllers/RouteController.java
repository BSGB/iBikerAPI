package com.ibiker.ibiker.Controllers;

import java.util.List;

import com.ibiker.ibiker.Exceptions.RouteNotFoundException;
import com.ibiker.ibiker.JWTokenVerifier;
import com.ibiker.ibiker.RouteService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.Route;
import com.ibiker.ibiker.Models.Routes;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping
    public ResponseEntity<?> postRoute(@RequestHeader("token") String userToken, @RequestBody Route route) {

        try {
            JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            routeService.postRoute(route);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getAllRoutes(@RequestHeader("token") String userToken) {

        try {
            JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final List<Route> routes;

        try {
            routes = routeService.getAllRoutes();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Routes(routes), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUserRoutes(@RequestHeader("token") String userToken) {

        final DecodedJWT jwt;

        try {
            jwt = JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        final List<Route> userRoutes;

        try {
            userRoutes = routeService.getAllUserRoutes(jwt.getIssuer());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new Routes(userRoutes), HttpStatus.OK);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<?> getRoute(@RequestHeader("token") String userToken, @PathVariable("routeId") String routeId) {

        try {
            JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        final Route route;

        try {
            route = routeService.getRouteById(routeId);
        } catch (RouteNotFoundException routeNotFound) {
            return new ResponseEntity<>(new Error(routeNotFound.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(route, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> putRoute(@RequestHeader("token") String userToken, @RequestBody Route route) {

        route.setId(new ObjectId(route.getStringifiedId()));

        try {
            JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        final Route savedRoute;

        try {
            savedRoute = routeService.putRoute(route);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(savedRoute, HttpStatus.OK);
    }

    @DeleteMapping("/{routeId}")
    public ResponseEntity<?> deleteRoute (@RequestHeader("token") String userToken, @PathVariable("routeId") String routeId) {

        try {
            JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.BAD_REQUEST);
        }

        boolean isSuccess = false;

        try {
            isSuccess = routeService.deleteRoute(routeId);
        } catch (RouteNotFoundException routeNotFound) {
            return new ResponseEntity<>(new Error(routeNotFound.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (!isSuccess) return new ResponseEntity<>(new Error("Could not delete all related files."), HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
