package com.telnetar.services.repositories.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.User;
import com.telnetar.services.repositories.UserRepositoryCustom;

public class UserRepositoryImpl implements UserRepositoryCustom {
	@Autowired
	EntityManager em;
	
	@Override
	public void update(User user) {
		Query query = em.createNamedQuery("User.update")
			.setParameter("password", user.getPassword())
			.setParameter("enabled", user.getEnabled())
			.setParameter("token", user.getToken())
			.setParameter("tokenExpired", user.getTokenExpired())
			.setParameter("pk", user.getPk());
		query.executeUpdate();
	}
}