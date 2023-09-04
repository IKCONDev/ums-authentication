package com.ikn.ums.authentication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ikn.ums.authentication.service.IUsersService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class UsersWebSecurity extends WebSecurityConfigurerAdapter {
	
	private Environment environment;
	
	private IUsersService service;
	
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	public UsersWebSecurity(Environment environment, IUsersService service,BCryptPasswordEncoder encoder){	
		this.environment = environment;
		this.service= service;
		this.encoder = encoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//disable csrf 
		http.cors().disable();
		http.csrf().disable();
		//provide authorization
		http.authorizeRequests()
		.antMatchers("/users/**")
		.permitAll()
		//.antMatchers("/users/login").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		//register your custom authentication filter with spring security
		.and()
		.addFilter(getAuthenticationFilter());
		
		//working with IP, instead of permit all
		//.hasIpAddress(env.getProperty("spring.cloud.gateway.ip"));
		
		//if you are using h2 as db, to view console
		//http.headers().frameOptions().disable();
	}
	
	public UserAuthenticationFilter getAuthenticationFilter() throws Exception{
		//set authentication manager on your auth filter
		UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(service,environment,authenticationManager());
		//set authentication manager on your auth filter
		//authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authenticationFilter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service).passwordEncoder(encoder);
	}
	
}
