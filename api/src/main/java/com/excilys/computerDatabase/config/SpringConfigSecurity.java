package com.excilys.computerDatabase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.excilys.computerDatabase.security.JwtAuthenticationEntryPoint;
import com.excilys.computerDatabase.security.JwtRequestFilter;
import com.excilys.computerDatabase.services.UserService;

@Configuration
@EnableWebSecurity
public class SpringConfigSecurity extends WebSecurityConfigurerAdapter {
    
    //private static Logger logger = LoggerFactory.getLogger(SpringConfigSecurity.class);

    @Autowired
    UserService userService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
       
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http
    	 .authorizeRequests()
         .antMatchers(HttpMethod.GET, "/computer/**","/company/**").hasAnyRole("user", "admin")
         .antMatchers(HttpMethod.POST,  "/computer/**","/company/**").hasRole("admin")
         .antMatchers(HttpMethod.PUT,  "/computer/**","/company/**").hasRole("admin")
         .antMatchers(HttpMethod.DELETE,  "/computer/**","/company/**").hasRole("admin")
         .antMatchers(HttpMethod.POST,  "/user/**").permitAll()
         .and()
         .csrf()
         .disable()
         .exceptionHandling()
         .authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	 http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
            
    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
}
}
