package com.excilys.springConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.WebApplicationInitializer;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.excilys.computerDatabase")
public class SpringConfiguration implements WebApplicationInitializer {

	  @Override
	    public void onStartup(ServletContext ctx) throws ServletException {
		  AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
			webContext.register(SpringConfiguration.class,SpringMvcConfiguration.class);
			webContext.setServletContext(ctx);
			
			ServletRegistration.Dynamic servlet=ctx.addServlet("dynamicServlet",new DispatcherServlet(webContext));
	        servlet.setLoadOnStartup(1);
	        servlet.addMapping("/");
	    }

	 
	 @Bean
	 public HikariDataSource getDatasource() {
		 return new HikariDataSource(new HikariConfig("/datasource.properties"));
	 }
}
