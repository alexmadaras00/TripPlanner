package org.example.serviceauth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.example.serviceauth.data.User;
import org.example.serviceauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class LoginService {
    @Value("${spring.secretKey}")
    private String secretKey;
    private static byte[] SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000; // 24 hours in milliseconds

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        SECRET_KEY = secretKey.getBytes();
    }

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(12); // You can specify the log rounds (e.g., 12)
        return BCrypt.hashpw(password, salt);
    }


    public static boolean checkPassword(String providedPassword, String storedHash) {
        return BCrypt.checkpw(providedPassword, storedHash);
    }

    public boolean saveUser(User user) {
        // Hash the password before saving
        user.setPassword(hashPassword(user.getPassword()));
        return Boolean.TRUE.equals(userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.just(false))
                .switchIfEmpty(userRepository.save(user).then(Mono.just(true))).block());
    }

    public Mono<String> checkUser(String username, String password) {
        return userRepository.findByUsername(username)
                .flatMap(existingUser -> {
                    if (checkPassword(password, existingUser.getPassword())) {
                        return Mono.just(generateToken(existingUser.getId()));
                    } else {
                        return Mono.error(new Exception("Wrong Password"));
                    }
                })
                .switchIfEmpty(Mono.error(new Exception("No Such User")));
    }

    public String generateToken(String username) {
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

    public static String getUsernameFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY);
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claims.getBody().getSubject();
    }
}
