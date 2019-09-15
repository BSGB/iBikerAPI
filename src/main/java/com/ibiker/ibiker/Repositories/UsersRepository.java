package com.ibiker.ibiker.Repositories;

import com.ibiker.ibiker.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String Email);
}
