package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Location;
import com.telnetar.model.Part;
import com.telnetar.model.User;
import com.telnetar.services.repositories.LocationRepositoryCustom;

public class LocationRepositoryImpl implements LocationRepositoryCustom {
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Location> findLocationByUserByPart(User user, Part part) {
		Query query = em.createNamedQuery("Location.findLocationByUserByPart")
				 .setParameter("pkUser", user.getPk())
				 .setParameter("pkPart", part.getPk());
		return (List<Location>)query.getResultList();
	}

}
