package com.ibiker.ibiker.Controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.ibiker.ibiker.JWTokenVerifier;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.Route;
import com.ibiker.ibiker.Models.RouteStop;
import com.ibiker.ibiker.Models.Routes;
import com.ibiker.ibiker.Repositories.RoutesRepository;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RoutesRepository routesRepository;

    @PostMapping
    public ResponseEntity<?> postRoute(@RequestBody Route route) throws IOException {

        ObjectId routeId = new ObjectId();

        route.setId(routeId);

        String userId = route.getUserId();

        final File file = new File("C:/user_data/" + userId + "/" + routeId);
        if (!file.exists()) {
            file.mkdirs();
        }

        for (RouteStop rs : route.getStops()) {
            final String SECRET_FILE_NAME = Long.toString(System.currentTimeMillis());

            try {
                final FileWriter fileWriter = new FileWriter(file + "/" + SECRET_FILE_NAME +".txt");
                final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(rs.getImageString());

                bufferedWriter.close();

                rs.setImageName(SECRET_FILE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            routesRepository.save(route);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRoutes(@RequestHeader("token") String userToken) {

        final DecodedJWT jwt;

        try {
            jwt = JWTokenVerifier.getVerifiedToken(userToken);
        } catch (TokenExpiredException e){
            return new ResponseEntity<>(new Error("Token has expired, please relog."), HttpStatus.UNAUTHORIZED);
        } catch (JWTVerificationException ex) {
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        final List<Route> userRoutes = routesRepository.findAllByUserId(jwt.getIssuer());
        userRoutes.forEach(route -> route.setStringifiedId(route.getId().toString()));

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

        final Optional<Route> route = routesRepository.findById(routeId);

        if (route.isEmpty()) return new ResponseEntity<>(new Error("An error occured."), HttpStatus.NOT_FOUND);

        route.get().setStringifiedId(route.get().getId().toString());

        //get pictures
        for (RouteStop rs : route.get().getStops()) {
            final String SECRET_FILE_NAME = rs.getImageName();
            final StringBuilder imageString = new StringBuilder();

            try {
                Scanner scanner = new Scanner(new File("C:/user_data/" + route.get().getUserId() + "/" + routeId + "/" + SECRET_FILE_NAME +".txt"));

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    imageString.append(line);
                }

                scanner.close();

                rs.setImageString(imageString.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        try {
            routesRepository.save(route);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
