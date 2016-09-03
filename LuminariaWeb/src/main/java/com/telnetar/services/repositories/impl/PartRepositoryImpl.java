package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Part;
import com.telnetar.model.Province;
import com.telnetar.model.User;
import com.telnetar.services.repositories.PartRepositoryCustom;

public class PartRepositoryImpl implements PartRepositoryCustom {
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Part> findPartByUserByProvince(User user, Province province) {
		Query query = em.createNamedQuery("Part.findPartByUserByProvince")
				 .setParameter("pkUser", user.getPk())
				 .setParameter("pkProvince", province.getPk());
		return (List<Part>)query.getResultList();
	}

}
