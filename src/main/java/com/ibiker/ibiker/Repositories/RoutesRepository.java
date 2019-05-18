package com.ibiker.ibiker.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibiker.ibiker.Models.Route;

public interface RoutesRepository extends MongoRepository<Route, String> {

}
