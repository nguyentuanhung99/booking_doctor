package com.cg.inventorypatientservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final com.cg.inventorypatientservice.service.JwtTokenProvider jwtTokenProvider;

  @Autowired
  private CorsConfigurationSource corsConfigurationSource;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // Disable CSRF (cross site request forgery)
    http.csrf().disable();
    
    //config cors spring security
    http.cors().configurationSource(corsConfigurationSource);
    
    // No session will be created or used by spring security
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Entry points
    http.authorizeRequests()//
        .antMatchers("/patient/signin").permitAll()//
        .antMatchers("/patient/signup").permitAll()//
        .antMatchers("/patient/register").permitAll()//
        .antMatchers("/patient/login").permitAll()//
        .antMatchers("/h2-console/**/**").permitAll()
        .antMatchers("/patient/myUser").access("hasRole('CLIENT')")
        .antMatchers("/patient/create-review").access("hasRole('CLIENT')")
        
        // Disallow everything else..
        .anyRequest().authenticated();

    // If a user try to access a resource without having enough permissions
    http.exceptionHandling().accessDeniedPage("/login");

    // Apply JWT
    http.apply(new com.cg.inventorypatientservice.service.JwtTokenFilterConfigurer(jwtTokenProvider));

    // Optional, if you want to test the API from a browser
    // http.httpBasic();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // Allow swagger to be accessed without authentication
    web.ignoring().antMatchers("/v2/api-docs")//
        .antMatchers("/swagger-resources/**")//
        .antMatchers("/swagger-ui.html")//
        .antMatchers("/configuration/**")//
        .antMatchers("/webjars/**")//
        .antMatchers("/public")
        
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
