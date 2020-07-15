package com.excilys.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@EnableWebMvc
@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer{
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/views/");
	    bean.setSuffix(".jsp");
	    return bean;
	   }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/js/**").addResourceLocations("/js/");
       registry.addResourceHandler("/css/**").addResourceLocations("/css/");
	}
}
