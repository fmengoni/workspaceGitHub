package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.Country;
import com.telnetar.model.Province;
import com.telnetar.model.User;
import com.telnetar.services.repositories.CountryRepository;
import com.telnetar.services.repositories.ProvinceRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.Marker;

@Service
public class ProvinceService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	ProvinceRepository provinceRepository;
	
	public List<Marker> findProvinceByUserByCountry(String username, Long pkCountry){
		List<User> lsUsers = userRepository.findByUsername(username); 
		if(lsUsers != null && lsUsers.size() == 1){
			User user =  lsUsers.get(0);
			Country country = countryRepository.findOne(pkCountry);
			List<Province> lsProvince = provinceRepository.findProvinceByUserByCountry(user, country); 
			List<Marker> lsMarkers = new ArrayList<Marker>();
			for (Province province : lsProvince) {
				lsMarkers.add(new Marker(province.getPk(), province.getName(), province.getLat(), province.getLng()));
			}
			return lsMarkers;
		}else{
			return null;
		}
	}
}
