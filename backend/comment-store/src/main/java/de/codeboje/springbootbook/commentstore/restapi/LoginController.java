package de.codeboje.springbootbook.commentstore.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class LoginController {

	@RequestMapping("authenticate")
	@ResponseStatus(code=HttpStatus.OK)
	public void authenticate() {
		
	}
}
