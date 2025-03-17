package org.example.serviceauth.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.example.serviceauth.data.User;
import org.example.serviceauth.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Service
public class LoginService {
    @Value("${spring.secretKey}")
    private String secretKey;
    private byte[] SECRET_KEY; // 24 hours in milliseconds
    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

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
                        return Mono.just(generateToken(existingUser.getUserId()));
                    } else {
                        return Mono.error(new Exception("Wrong Password"));
                    }
                })
                .switchIfEmpty(Mono.error(new Exception("No Such User")));
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        Key key = new SecretKeySpec(SECRET_KEY, SignatureAlgorithm.HS512.getJcaName());

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
        System.out.println("Generated Token: " + token);
        return token;
    }
    public String refreshToken(String refreshToken){
        if(validateRefreshToken(refreshToken)){
            return generateNewAccessToken(refreshToken);
        }else{
            throw new RuntimeException("Invalid Refresh Token");
        }
    }

    private String generateNewAccessToken(String refreshToken) {
        String username = getUsernameFromToken(refreshToken);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String generateNewRefreshToken() {
        return Jwts.builder()
                .setSubject("username") // Usually taken from an existing session or user info
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    public String getUsernameFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateRefreshToken(String refreshToken) {
        // Add validation logic for the refresh token (check expiration, signature, etc.)
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();  // Assuming username is stored as subject
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);  // More specific error handling
        }
    }

}
