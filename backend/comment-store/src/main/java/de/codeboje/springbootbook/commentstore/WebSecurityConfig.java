package de.codeboje.springbootbook.commentstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import de.codeboje.springbootbook.commentstore.security.CommentStoreAuthenticationSuccessHandler;
import de.codeboje.springbootbook.commentstore.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



	@Configuration
	@Order(1)
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		@Autowired
		private CommentStoreAuthenticationSuccessHandler authenticationSuccessHandler;

		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**").csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and().cors() // make
																										// cors
																										// work
																										// with
																										// security
																										// filter
					.and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
					.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/preauth").permitAll()
					.anyRequest().authenticated().and().httpBasic().and().formLogin()

					.loginProcessingUrl("/authenticate").successHandler(authenticationSuccessHandler)
					.failureHandler(new SimpleUrlAuthenticationFailureHandler()).and().logout()
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
		}
	}
	
	@Configuration
	@Order(2)
	public static class UIWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and().authorizeRequests()
			.antMatchers("/").authenticated()
			.antMatchers("/res/**").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().defaultSuccessUrl("/", true)
					.and().logout().permitAll();
		}

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("mypassword").roles("ADMIN");
	}
	
	@Bean
	@Primary
	public AuthenticationSuccessHandler defaultSuccessHandler() {
		return new SavedRequestAwareAuthenticationSuccessHandler();
	}
	
	@Bean
	public CommentStoreAuthenticationSuccessHandler mySuccessHandler() {
		return new CommentStoreAuthenticationSuccessHandler();
	}

}
