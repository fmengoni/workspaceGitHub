package com.telnetar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.System;
import com.telnetar.services.repositories.SystemRepository;

@Service
public class SystemService {
	@Autowired
	private SystemRepository systemRepository;

	public System findOne(Long pk){
		return systemRepository.findOne(pk);
	}
}
