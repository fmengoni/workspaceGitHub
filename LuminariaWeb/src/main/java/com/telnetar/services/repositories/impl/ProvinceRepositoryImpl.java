package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Country;
import com.telnetar.model.Province;
import com.telnetar.model.User;
import com.telnetar.services.repositories.ProvinceRepositoryCustom;

public class ProvinceRepositoryImpl implements ProvinceRepositoryCustom {
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Province> findProvinceByUserByCountry(User user, Country country) {
		Query query = em.createNamedQuery("Province.findProvinceByUserByCountry")
				 .setParameter("pkUser", user.getPk())
				 .setParameter("pkCountry", country.getPk());
		return (List<Province>)query.getResultList();
	}

}
