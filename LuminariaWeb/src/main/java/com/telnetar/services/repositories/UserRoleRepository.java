package com.telnetar.services.repositories;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long>{
	
}
