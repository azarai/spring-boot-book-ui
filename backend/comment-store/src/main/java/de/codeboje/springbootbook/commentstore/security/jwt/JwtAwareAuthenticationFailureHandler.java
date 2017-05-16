package de.codeboje.springbootbook.commentstore.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * Handles authentication failure during JWT auth
 *
 */
@Component
public class JwtAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private static Logger LOGGER = LoggerFactory.getLogger(JwtAwareAuthenticationFailureHandler.class);
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		LOGGER.info("JWT Auth failed", e);

	}

}
