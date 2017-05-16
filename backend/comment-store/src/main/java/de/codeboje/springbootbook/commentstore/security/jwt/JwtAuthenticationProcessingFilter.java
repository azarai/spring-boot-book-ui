package de.codeboje.springbootbook.commentstore.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * A Spring Security Filter for login via JWT
 *
 */
public class JwtAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationFailureHandler failureHandler;
    private final JwtRequestExtractor tokenExtractor;
    
    @Autowired
    public JwtAuthenticationProcessingFilter(AuthenticationFailureHandler failureHandler, 
    		JwtRequestExtractor tokenExtractor, String path) {
        super(path);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
    	if (super.requiresAuthentication(request, response)) {
    		
    		if (HttpMethod.OPTIONS.name().equalsIgnoreCase(request.getMethod())) {
    			return false;
    		}
    		Authentication existingAuth = SecurityContextHolder.getContext()
    				.getAuthentication();

    		if (existingAuth == null || !existingAuth.isAuthenticated()) {
    			return true;
    		}
    	}

		return false;
	}

	@Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(tokenExtractor.extract(request)));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
