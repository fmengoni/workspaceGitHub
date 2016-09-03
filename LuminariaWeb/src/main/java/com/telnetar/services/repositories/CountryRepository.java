package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Country;
import com.telnetar.model.User;

public interface CountryRepository extends CrudRepository<Country, Long>, CountryRepositoryCustom{
	public List<Country> findCountries(User user); 
}
