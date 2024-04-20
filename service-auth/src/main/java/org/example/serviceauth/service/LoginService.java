package org.example.serviceauth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.serviceauth.data.User;
import org.example.serviceauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;


@Service
public class LoginService {

    private static final byte[] SECRET_KEY = "VatjGhXrwQzdPCulVZx8bjdInu4U4TAlTtKgBS6xDFrjmRAlNILFctMAxktWimUc".getBytes(); // Change this to your secret key as byte array
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds



    @Autowired
    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String providedPassword, String storedHash) {
        // Use BCrypt to verify the password
        return BCrypt.checkpw(providedPassword, storedHash);
    }

    public boolean saveUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername()).block();
        if (existingUser != null) {
            return false;
        }
        user.setPassword(hashPassword(user.getPassword()));
        Mono<User> ur = userRepository.save(user);
        ur.subscribe(savedUser -> {}, error -> {});
        return true;
    }

    public String checkUser(User user) throws Exception {
        User existingUser = userRepository.findByUsername(user.getUsername()).block();
        if (existingUser == null) {
            throw new Exception("No Such User");
        }
        boolean verifiedUser = checkPassword(user.getPassword(), existingUser.getPassword());
        if (!verifiedUser) {
            throw new Exception("Wrong Password");
        }
        return existingUser.getId() + "||" + generateToken(existingUser.getId());
    }

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        Key key = new SecretKeySpec(SECRET_KEY, SignatureAlgorithm.HS512.getJcaName());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public static boolean verifyJwtId(String id, String token) {
        System.out.println(getUsernameFromToken(token));
        return getUsernameFromToken(token).equals(id);
    }

    public static String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY);
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

}

