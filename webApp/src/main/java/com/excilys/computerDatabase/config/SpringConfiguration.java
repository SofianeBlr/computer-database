package com.excilys.computerDatabase.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({ SpringConfigService.class,SpringConfigDao.class, WebAppInitializer.class, 
	 SpringConfigWebApp.class})
public class SpringConfiguration {

}
