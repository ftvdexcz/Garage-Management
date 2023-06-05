package com.garagemanagement.userservice.common.config.security;

import com.garagemanagement.userservice.common.constant.JWT;
import com.garagemanagement.userservice.common.constant.StatusCodeResponse;
import com.garagemanagement.userservice.common.entity.User;
import com.garagemanagement.userservice.common.handler.AppError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.garagemanagement.userservice.common.model.RetrieveUserDTO.withRole;

@Component
public class JwtTokenProvider {

    private String MY_SECRET_KEY = JWT.jwtSecret;
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private int jwtExpirationInMs = JWT.jwtExpirationInMs;

    // Use this function to create new token when signup, login
    // Sign using ID of user
    public Map<String, String> signToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Map<String, String> map = new HashMap<>();

        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("fullname", user.getFullname());
        map.put("phone", user.getPhone());
        map.put("email", user.getEmail());
        map.put("address", user.getAddress());
        map.put("role", withRole(user.getRole()));

        String token =  Jwts.builder()
                .setSubject(user.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(new SecretKeySpec(MY_SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
                .compact();
        map.put("token", token);
        return map;
    }

    // Use this function to decode token
    // Token is valid if return a String (ID of user), otherwise token is invalid
    public String validateToken(String token) {
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(MY_SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            return claims.getSubject();
        }catch(Exception ex){
            System.out.println("Error when decode");
            return null;
        }
    }

    // Use this function to delete token by set expire by add 2s to current time
    public String deleteToken(String token){
        try{
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(new SecretKeySpec(MY_SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                    .build().parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            String id = claims.getSubject();
            System.out.println(id);
            Date expirationDate = claims.getExpiration();
            System.out.println(expirationDate);
            // sign new key, set expiration by add 2s to the current time
            Date newExpirationDate = new Date(System.currentTimeMillis() + 2000);
            System.out.println(newExpirationDate);
            // Create a new token with the same claims and new expiration date
            return Jwts.builder()
                    .setSubject(id)
                    .setIssuedAt(new Date())
                    .setExpiration(newExpirationDate)
                    .signWith(new SecretKeySpec(MY_SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName()), SignatureAlgorithm.HS256)
                    .compact();
        }catch(Exception e){
            throw AppError.UnauthorizedError();
        }
    }

    // Parse token from cookie or Authorization Header
    public String getTokenFromRequest(HttpServletRequest request){
        String token = null;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie: cookies){
                System.out.println(cookie);
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    return token;
                }
            }
        }

        token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer")){
            token = token.split(" ")[1];
        }
//        System.out.println(token);
        return token;
    }
}
