package com.telnetar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telnetar.model.UserSystem;
import com.telnetar.services.repositories.UserSystemRepository;

@Service
@Transactional
public class UserSystemService {
	@Autowired
	private UserSystemRepository userSystemRepository;

	public UserSystem findOne(Long pk){
		return userSystemRepository.findOne(pk);
	}
}
