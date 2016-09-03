package com.telnetar.configurations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.SessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.telnetar.Constants;
import com.telnetar.Util;
import com.telnetar.exceptions.TelnetarException;
import com.telnetar.model.User;
import com.telnetar.model.UserRole;
import com.telnetar.nodejs.NodeConnectDto;
import com.telnetar.nodejs.TelnetarIOCallback;
import com.telnetar.services.UserService;

import io.socket.SocketIO;

@Component
public class TelnetarAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	private UserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try{
			String password = (String) authentication.getCredentials();
			User user = userService.findByUsername(authentication.getName());
			
			if(!user.getEnabled().equals(Constants.ACTIVE)){
				throw new LockedException("Usuario bloqueado");
			}
			if(!Util.comparePasswords(password, user.getPassword())){
				throw new BadCredentialsException(Constants.MSG_USER_PASS_EXCEPTION);
			}
			
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			for (Iterator<UserRole> iterator = user.getUserRoles().iterator(); iterator.hasNext();) {
				UserRole userRole = iterator.next();
				authorities.add(new SimpleGrantedAuthority(userRole.getRole()));
			}
			user.setToken(new Double(Math.random()*9999999).toString());
			user.setTokenExpired(Util.addToDate(new Date(), 0, 0, 0, 1, 0, 0));
			userService.update(user);
			
			Gson gson = new Gson();
		    String json = gson.toJson(new NodeConnectDto("Web", authentication.getName()));
		    Properties properties = new Properties();
		    properties.put("json", json);
			SocketIO socketIO = new SocketIO(Constants.NODE_JS_SERVER_LINK, properties);
			socketIO.connect(new TelnetarIOCallback());
			
			HttpServletRequest httpReq = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			httpReq.getSession().setAttribute("socketio", socketIO);
			httpReq.getSession().setAttribute("user", user);
			
			return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials(), authorities);
		}catch (TelnetarException e) {
			throw new BadCredentialsException(e.getMessage());
		}catch (Exception e) {
			throw new SessionException("Se produjo un error inesperado");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
	
}
