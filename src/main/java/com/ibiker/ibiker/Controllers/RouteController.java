package com.ibiker.ibiker.Controllers;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibiker.ibiker.Models.Error;
import com.ibiker.ibiker.Models.Route;
import com.ibiker.ibiker.Repositories.RoutesRepository;

@RestController
@RequestMapping("/api/route")
public class RouteController {
	
    @Autowired
    private RoutesRepository routesRepository;

    @PostMapping
    public ResponseEntity<?> saveRoute(@RequestBody Route route) {
    	
    	route.set_id(ObjectId.get());
    	
    	try {
    		routesRepository.save(route);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new ResponseEntity<>(new Error("An error occured."), HttpStatus.BAD_REQUEST);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
}
