package com.excilys.computerDatabase.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {

	 @Override
	    public void onStartup(ServletContext ctx) throws ServletException {
		  AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
			webContext.register(WebAppInitializer.class,SpringConfigWebApp.class, SpringConfigService.class,SpringConfigDao.class,SpringConfigSecurity.class);
			webContext.setServletContext(ctx);
			
			ServletRegistration.Dynamic servlet=ctx.addServlet("dynamicServlet",new DispatcherServlet(webContext));
	        servlet.setLoadOnStartup(1);
	        servlet.addMapping("/");
	    }

}
