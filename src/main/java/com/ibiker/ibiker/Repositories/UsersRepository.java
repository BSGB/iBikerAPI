package com.ibiker.ibiker.Repositories;

import com.ibiker.ibiker.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
    User findUserByEmail(String Email);
}
