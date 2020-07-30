package com.excilys.computerDatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.excilys.computerDatabase.restControllers","com.excilys.computerDatabase.security"})
public class SpringConfigRestController {

}
