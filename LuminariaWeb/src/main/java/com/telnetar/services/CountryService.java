package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.Country;
import com.telnetar.model.User;
import com.telnetar.services.repositories.CountryRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.*;

@Service
public class CountryService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CountryRepository countryRepository;
	
	public List<Marker> findCountriesByUser(String username){
		List<User> lsUsers = userRepository.findByUsername(username); 
		if(lsUsers != null && lsUsers.size() == 1){
			User user =  lsUsers.get(0);
			List<Country> lsCountries = countryRepository.findCountries(user); 
			List<Marker> lsMarkers = new ArrayList<Marker>();
			for (Country country : lsCountries) {
				lsMarkers.add(new Marker(country.getPk(), country.getName(), country.getLat(), country.getLng()));
			}
			return lsMarkers;
		}else{
			return null;
		}
	}
}
