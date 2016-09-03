package com.telnetar.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	TelnetarAuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/assets/**", "/bootstrap/**", "/plugins/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/luminarias/luminariasPorPais").permitAll()
			.antMatchers("/").authenticated().anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").permitAll()
			.and()
//			.logout().permitAll();
			.csrf().disable()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
			;
	}
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}