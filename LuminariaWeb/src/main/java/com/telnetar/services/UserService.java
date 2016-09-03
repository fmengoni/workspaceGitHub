package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telnetar.model.User;
import com.telnetar.model.UserRole;
import com.telnetar.model.UserSystem;
import com.telnetar.Roles;
import com.telnetar.exceptions.TelnetarException;
import com.telnetar.model.System;
import com.telnetar.services.repositories.SystemRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.services.repositories.UserRoleRepository;
import com.telnetar.services.repositories.UserSystemRepository;

@Service
@Transactional(rollbackFor = {DataIntegrityViolationException.class})
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SystemRepository systemRepository;
	@Autowired
	private UserSystemRepository userSystemRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Transactional
	public void register(User user){
		System system = systemRepository.findOne(new Long(1));
		
		UserSystem userSystem = new UserSystem();
		userSystem.setUser(user);
		userSystem.setSystem(system);
		List<UserSystem> lsUserSystems = new ArrayList<UserSystem>();
		lsUserSystems.add(userSystem);
		
		UserRole userRole = new UserRole();
		userRole.setRole(Roles.ROLE_USER.getRole());
		userRole.setUser(user);
		List<UserRole> lsRoles = new ArrayList<UserRole>();
		lsRoles.add(userRole);
		
		user.setUserSystems(lsUserSystems);
		user.setUserRoles(lsRoles);
		user.setEnabled(new Integer(1));
		
		userRepository.save(user);
		userSystemRepository.save(userSystem);
		userRoleRepository.save(userRole);
	}
	
	public List<User> findAll(){
		return (List<User>) userRepository.findAll();
	}
	
	public User findOne(Long pk){
		return userRepository.findOne(pk);
	}

	@Transactional
	public void update(User user){
		userRepository.update(user);
	}

	public User findByUsername(String name) throws TelnetarException {
		List<User> lsUser = userRepository.findByUsername(name);
		if(lsUser == null || lsUser.isEmpty()){
			throw new TelnetarException("No existe ningún usuario registrado con el nombre " + name);
		}
		return lsUser.get(0);
	}
}