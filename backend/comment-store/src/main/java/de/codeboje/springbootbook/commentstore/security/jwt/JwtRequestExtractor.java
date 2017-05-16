package de.codeboje.springbootbook.commentstore.security.jwt;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;


public class JwtRequestExtractor {
	
	public static String HEADER_PREFIX = "Bearer ";
	
    public String extract(HttpServletRequest request) {
    	
    	final String authHeader = request.getHeader("Authorization");
    	if (authHeader != null) {
            if (StringUtils.isBlank(authHeader)) {
                throw new AuthenticationServiceException("Authorization header cannot be blank!");
            }

            if (authHeader.length() <= HEADER_PREFIX.length()) {
                throw new AuthenticationServiceException("Invalid authorization header size.");
            }

            return authHeader.substring(HEADER_PREFIX.length(), authHeader.length());
    	}

    	throw new AuthenticationServiceException("Authorization header is missing");
    }
}
