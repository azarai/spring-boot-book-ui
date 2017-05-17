package de.codeboje.springbootbook.commentstore.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTGenerator {

	@Autowired
	private JwtSettings settings;
	
	public String create(User user) {
        Claims claims = Jwts.claims()
        		.setSubject(user.getUsername());
        claims.put("scopes", user.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        
        Instant currentTime = Instant.now();

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(Date.from(currentTime))
          .setExpiration(Date.from(currentTime.plus(settings.getTokenExpirationTime(), ChronoUnit.MINUTES)))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();
        
        return token;
	}
}
