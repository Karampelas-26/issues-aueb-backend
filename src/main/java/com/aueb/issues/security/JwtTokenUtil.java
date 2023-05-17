package com.aueb.issues.security;

import com.aueb.issues.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author George Karampelas
 */

@Component
public class JwtTokenUtil {

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; //24 hours
    private static final String SECRET_KEY = "786b3d0c6b9fb11df3a2f345697d142f1f0e0395ace69fd67001e9a0f182d9540ae18606abbe1fb4ddcfab2eb68a280b694e67112d55ef541f603514e229c2e6";


    public String generateAccessToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
