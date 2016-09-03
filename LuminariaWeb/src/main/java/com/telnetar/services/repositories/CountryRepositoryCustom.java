package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Country;
import com.telnetar.model.User;

public interface CountryRepositoryCustom {
	public List<Country> findCountries(User user); 
}
