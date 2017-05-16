package de.codeboje.springbootbook.commentstore.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Authenticates a JWT in Spring Security
 *
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

	private final JwtSettings jwtSettings;

	@Autowired
	public JwtAuthenticationProvider(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String rawAccessToken = (String) authentication.getCredentials();

		Jws<Claims> jwsClaims = parseClaims(jwtSettings.getTokenSigningKey(), rawAccessToken);
		String subject = jwsClaims.getBody().getSubject();
		List<String> scopes = jwsClaims.getBody().get("scopes", List.class);

		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		for (String scope : scopes) {
			roles.add(new SimpleGrantedAuthority(scope));
		}
		
		final User context = new User(subject, "makeuser happy, it's not needed", roles);

		return new JwtAuthenticationToken(context, context.getAuthorities());
	}

	public Jws<Claims> parseClaims(String signingKey, String token) {
		try {
			return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
		} catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
			logger.error("Invalid JWT Token", ex);
			throw new BadCredentialsException("Invalid JWT token: ", ex);
		} catch (ExpiredJwtException expiredEx) {
			logger.info("JWT Token is expired", expiredEx);
			throw new BadCredentialsException("JWT Token expired", expiredEx);
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
