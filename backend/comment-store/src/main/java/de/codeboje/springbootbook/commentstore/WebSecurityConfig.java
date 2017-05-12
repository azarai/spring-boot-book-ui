package de.codeboje.springbootbook.commentstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import de.codeboje.springbootbook.commentstore.security.CommentStoreAuthenticationSuccessHandler;
import de.codeboje.springbootbook.commentstore.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CommentStoreAuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.and()
		.cors() //make cors work with security filter
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(restAuthenticationEntryPoint)
		.and()
		.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.antMatchers("/preauth").permitAll()
			.anyRequest().authenticated()
			.and()
				.httpBasic()
			.and()
				.formLogin()
				
				.loginProcessingUrl("/authenticate")
					.successHandler(authenticationSuccessHandler)
					.failureHandler(new SimpleUrlAuthenticationFailureHandler())
					.and()
				.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("mypassword").roles("ADMIN");
	}

	@Bean
	public CommentStoreAuthenticationSuccessHandler mySuccessHandler() {
		return new CommentStoreAuthenticationSuccessHandler();
	}

}
