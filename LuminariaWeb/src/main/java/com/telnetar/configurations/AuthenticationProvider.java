package com.telnetar.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
public class AuthenticationProvider {
	@Bean(name = "dataSource")
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("root");
		return driverManagerDataSource;
	}
	
	@Bean(name="userDetailsService")
	public UserDetailsService userDetailsService(){
		JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
		jdbcImpl.setDataSource(dataSource());
		jdbcImpl.setUsersByUsernameQuery("select a.username,a.password,a.enabled from sso.users a, sso.user_systems b where a.pk = b.pk_user and b.pk_system = 1 and a.username=?");
		jdbcImpl.setAuthoritiesByUsernameQuery("select a.username, c.role from sso.users a, sso.user_systems b, sso.user_roles c where a.enabled = 1 and username=? and a.pk = b.pk_user and b.pk_system = 1 and c.pk_user = a.pk");
		return jdbcImpl;
	}
}
