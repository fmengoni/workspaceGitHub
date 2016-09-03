package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Country;
import com.telnetar.model.User;
import com.telnetar.services.repositories.CountryRepositoryCustom;

public class CountryRepositoryImpl implements CountryRepositoryCustom{
	@Autowired
	EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Country> findCountries(User user){
		 Query query = em.createNamedQuery("Country.findCountriesByUser")
				 .setParameter("pkUser", user.getPk());
		return (List<Country>)query.getResultList();
	}

}
