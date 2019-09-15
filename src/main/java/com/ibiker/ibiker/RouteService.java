package com.ibiker.ibiker;

import com.ibiker.ibiker.Exceptions.RouteNotFoundException;
import com.ibiker.ibiker.Interfaces.IRouteService;
import com.ibiker.ibiker.Models.Route;
import com.ibiker.ibiker.Models.RouteStop;
import com.ibiker.ibiker.Repositories.RoutesRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class RouteService implements IRouteService {

    private final RoutesRepository routesRepository;

    @Autowired
    public RouteService(RoutesRepository routesRepository) {
        this.routesRepository = routesRepository;
    }

    public Route postRoute(Route route) throws IOException {
        final ObjectId routeId = new ObjectId();
        final String userId = route.getUserId();
        final File file = new File("C:/user_data/" + userId + "/" + routeId);

        route.setId(routeId);

        if (!file.exists()) {
            file.mkdirs();
        }

        for (RouteStop rs : route.getStops()) {
            final String SECRET_FILE_NAME = Long.toString(System.currentTimeMillis());

            final FileWriter fileWriter = new FileWriter(file + "/" + SECRET_FILE_NAME + ".txt");
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(rs.getImageString());

            bufferedWriter.close();

            rs.setImageName(SECRET_FILE_NAME);
        }

        return routesRepository.save(route);
    }

    public Route getRouteById(String routeId) throws RouteNotFoundException, FileNotFoundException {
        final Optional<Route> routeOptional = routesRepository.findById(routeId);

        if (routeOptional.isEmpty()) throw new RouteNotFoundException();

        final Route route = routeOptional.get();

        route.setStringifiedId(route.getId().toString());

        for (RouteStop rs : route.getStops()) {
            final String SECRET_FILE_NAME = rs.getImageName();
            final StringBuilder imageString = new StringBuilder();
            final Scanner scanner = new Scanner(new File("C:/user_data/" + route.getUserId() +
                    "/" + routeId + "/" + SECRET_FILE_NAME + ".txt"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                imageString.append(line);
            }

            rs.setImageString(imageString.toString());

            scanner.close();
        }
        return route;
    }

    public Route putRoute(Route route) {
        return routesRepository.save(route);
    }

    public boolean deleteRoute(String routeId) throws RouteNotFoundException {
        final Optional<Route> routeOptional = routesRepository.findById(routeId);

        if (routeOptional.isEmpty()) throw new RouteNotFoundException();

        final Route route = routeOptional.get();
        final String userId = route.getUserId();
        final File folder = new File("C:/user_data/" + userId + "/" + routeId);

        routesRepository.delete(route);

        return deleteFolder(folder);
    }

    public List<Route> getAllUserRoutes(String issuer) {
        final List<Route> userRoutes = routesRepository.findAllByUserId(issuer);
        userRoutes.forEach(route -> route.setStringifiedId(route.getId().toString()));

        return userRoutes;
    }

    public List<Route> getAllRoutes() {
        final List<Route> usersRoutes = routesRepository.findAll().stream()
                .filter(Route::getIsPublished)
                .collect(Collectors.toList());
        usersRoutes.forEach(route -> route.setStringifiedId(route.getId().toString()));
        return usersRoutes;
    }

    private Boolean deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        return folder.delete();
    }
}
