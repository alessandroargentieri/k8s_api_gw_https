package com.quicktutorialz.javalin.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;

import static com.quicktutorialz.javalin.domain.Constants.Envs.JWT_SECRET_KEY;
import static com.quicktutorialz.javalin.domain.Constants.JWT_DATA;
import static com.quicktutorialz.javalin.domain.Constants.JWT_ISSUER;
import static com.quicktutorialz.javalin.domain.env.EnvVarRegistry.getEnv;

public class JwtUtils {

    public static String generateJwt(String encryptedPayload) {
        Algorithm algorithm = Algorithm.HMAC256( getEnv(JWT_SECRET_KEY) );
        return JWT.create()
                .withIssuer(JWT_ISSUER)
                .withClaim(JWT_DATA, encryptedPayload)
                .withExpiresAt(getExpirationDate())
                .sign(algorithm);
    }

    public static Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

    public static String verifyJwt(String jwt) {
        try {
            Algorithm algorithm = Algorithm.HMAC256( getEnv(JWT_SECRET_KEY) );
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .build();
            DecodedJWT decoded = verifier.verify(jwt);
            return decoded.getPayload();
        } catch (JWTVerificationException exception){
            return null;
        }
    }
}
