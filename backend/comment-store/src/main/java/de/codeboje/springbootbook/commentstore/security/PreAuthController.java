package de.codeboje.springbootbook.commentstore.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple endpoint for the sole purpose that the UI can call it and retrieve a XSRF Token Cookie by Spring Security
 *
 */
@RestController
public class PreAuthController {
	
		@RequestMapping("/preauth")
		public void csrf() {
		}
}
