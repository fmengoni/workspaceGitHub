package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.User;

public interface UserRepository extends CrudRepository<User, Long>, UserRepositoryCustom{

	public List<User> findByUsername(String username);
	
	public void update(User user);

}
