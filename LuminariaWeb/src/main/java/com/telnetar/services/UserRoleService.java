package com.telnetar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telnetar.model.UserRole;
import com.telnetar.services.repositories.UserRoleRepository;

@Service
@Transactional
public class UserRoleService {
	@Autowired
	private UserRoleRepository userRoleRepository;

	public UserRole findOne(Long pk){
		return userRoleRepository.findOne(pk);
	}
}
