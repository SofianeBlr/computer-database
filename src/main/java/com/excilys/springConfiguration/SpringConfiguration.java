package com.excilys.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.computerDatabase")
public class SpringConfiguration extends AbstractContextLoaderInitializer {

	 @Override
	 protected WebApplicationContext createRootApplicationContext() {
	 AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	 rootContext.register(SpringConfiguration.class);
	 return rootContext;
	 }
	 
	 @Bean
	 public HikariDataSource getDatasource() {
		 return new HikariDataSource(new HikariConfig("/datasource.properties"));
	 }
}
