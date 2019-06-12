package com.ibiker.ibiker;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JWTokenVerifier {

    public static DecodedJWT getVerifiedToken(String userToken) {
        Algorithm algorithm = Algorithm.HMAC512(JWTSecretKeyProvider.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        return verifier.verify(userToken);
    }
}
