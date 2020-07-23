package com.excilys.computerDatabase.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.excilys.computerDatabase.daos","com.excilys.computerDatabase.services"})
public class CliConfiguration {
	@Bean
	 public HikariDataSource getDatasource() {
		 return new HikariDataSource(new HikariConfig("/datasource.properties"));
	 }
	 @Bean
	 public PlatformTransactionManager transactionManager() {
	     JpaTransactionManager transactionManager = new JpaTransactionManager();
	     transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
	  
	     return transactionManager;
	 }
	 @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(getDatasource());
	      em.setPackagesToScan(new String[]{ "com.excilys.computerDatabase.models" });

	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      return em;
	   }
}
