package de.codeboje.springbootbook.commentstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class StaticResourceConfiguration extends WebMvcConfigurerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(StaticResourceConfiguration.class);

	@Value("${static.path}")
	private String staticPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		if (staticPath != null) {
			LOG.info("Serving static content from " + staticPath);
			registry.addResourceHandler("/res/**").addResourceLocations("file:" + staticPath);
		}
	}
}
