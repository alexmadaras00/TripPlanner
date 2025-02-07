//package org.example.finddestinationservice.unit;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.example.servicedestinationrecommender.config.JwtUtil;
//
//public class JwtTest {
//    public static void main(String[] args) {
//        String token = JwtUtil.generateToken(123);
//        System.out.println("Generated Token: " + token);
//
//        int userId = getUserIdFromJwt(token);
//        System.out.println("Extracted User ID: " + userId);
//    }
//
//    private static int getUserIdFromJwt(String jwt) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(JwtUtil.getKey().getEncoded())
//                    .build()
//                    .parseClaimsJws(jwt)
//                    .getBody();
//            return (int) claims.get("user_id");
//        } catch (Exception e) {
//            System.err.println("Error parsing JWT: " + e.getMessage());
//            throw new RuntimeException("Invalid JWT token", e);
//        }
//    }