package de.codeboje.springbootbook.commentstore.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.codeboje.springbootbook.commentstore.security.jwt.JWTGenerator;

@Controller
public class LoginController {
	
	@Autowired
	private JWTGenerator jwtGen;

	@RequestMapping("authenticate")
	@ResponseStatus(code=HttpStatus.OK)
	@ResponseBody
	public String authenticate(Authentication authentication) {
		final User user = (User) authentication.getPrincipal();
		final String token = jwtGen.create(user);
		System.err.println(user);
		
		return token;
	}
}
