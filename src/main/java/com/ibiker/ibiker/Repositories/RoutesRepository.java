package com.ibiker.ibiker.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibiker.ibiker.Models.Route;

public interface RoutesRepository extends MongoRepository<Route, String> {
	List<Route> findAllByUserId(String userId);
}
