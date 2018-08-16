package com.in28minutes.rest.webservices.restfulwebservices;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.models.Contact;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final Contact DEFAULT_CONTACT = new Contact().email("my@email.xyz");
	
	private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("API TITLE", "Api Description", "1.0", "urn:tos",
	          DEFAULT_CONTACT.getEmail(), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

	private static final Set<String> DEFAULT_CONSUMES_PRODUCES = new HashSet<String>(Arrays.asList("application/json", "application/xml"));
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO)
				.consumes(DEFAULT_CONSUMES_PRODUCES)
				.produces(DEFAULT_CONSUMES_PRODUCES);
	}
}
