package com.excilys.computerDatabase.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.excilys.computerDatabase.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringConfigSecurity extends WebSecurityConfigurerAdapter {
    
    //private static Logger logger = LoggerFactory.getLogger(SpringConfigSecurity.class);

    @Autowired
    UserService userService;
  
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
       
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.authorizeRequests()
         .antMatchers(HttpMethod.GET, "/", "/dashboard/**").hasAnyRole("user", "admin")
         .antMatchers(HttpMethod.POST, "/", "/dashboard/**").hasRole("admin")
         .antMatchers("/addComputer/**").hasRole("admin")
         .antMatchers("/editComputer/**").hasRole("admin")
         .and()
         .logout()
         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))            
         .logoutSuccessUrl("/dashboard")
         .invalidateHttpSession(true)
         .deleteCookies("JSESSIONID")        
         .and()
         .httpBasic()
         .and()
         .csrf()
         .disable()
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
    }

    DigestAuthenticationFilter digestAuthenticationFilter() throws Exception {
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setUserDetailsService(userService);
        digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
        return digestAuthenticationFilter;
    }
    
    @Bean
    DigestAuthenticationEntryPoint digestEntryPoint() {
        DigestAuthenticationEntryPoint bauth = new DigestAuthenticationEntryPoint();
        bauth.setRealmName("digest auth");
        bauth.setKey("MyKey");
        return bauth;
    }
}
