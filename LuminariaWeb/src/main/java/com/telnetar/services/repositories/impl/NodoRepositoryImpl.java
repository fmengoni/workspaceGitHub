package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Location;
import com.telnetar.model.Nodo;
import com.telnetar.model.User;
import com.telnetar.services.repositories.NodoRepositoryCustom;

public class NodoRepositoryImpl implements NodoRepositoryCustom {
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Nodo> findNodoByUserByLocation(User user, Location location) {
		Query query = em.createNamedQuery("Nodo.findNodoByUserByLocation")
				 .setParameter("pkUser", user.getPk())
				 .setParameter("pkLocation", location.getPk());
		return (List<Nodo>)query.getResultList();
	}
}