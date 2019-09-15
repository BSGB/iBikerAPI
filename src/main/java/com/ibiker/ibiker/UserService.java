package com.ibiker.ibiker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ibiker.ibiker.Exceptions.UserNotFoundException;
import com.ibiker.ibiker.Exceptions.WrongPasswordException;
import com.ibiker.ibiker.Interfaces.IUserService;
import com.ibiker.ibiker.Models.AuthData;
import com.ibiker.ibiker.Models.User;
import com.ibiker.ibiker.Repositories.UsersRepository;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String loginUser(AuthData authData, Long expiration, String secretKey) {
        final Optional<User> optionalUser = usersRepository.findUserByEmail(authData.getUserEmail());

        if (optionalUser.isEmpty()) throw new UserNotFoundException();

        final User user = optionalUser.get();

        if (!passwordEncoder.matches(authData.getUserPassword(), user.getPassword())) throw new WrongPasswordException();

        final String userId = user.getId().toString();

        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuer(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secretKey.getBytes()));
    }

    public User registerUser(AuthData authData) {
        final User user = new User(ObjectId.get(),
                authData.getUserEmail(),
                passwordEncoder.encode(authData.getUserPassword()), System.currentTimeMillis());

        return usersRepository.save(user);
    }
}
