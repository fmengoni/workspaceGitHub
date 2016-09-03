package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.Part;
import com.telnetar.model.Province;
import com.telnetar.model.User;
import com.telnetar.services.repositories.PartRepository;
import com.telnetar.services.repositories.ProvinceRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.Marker;

@Service
public class PartService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProvinceRepository provinceRepository;
	@Autowired
	PartRepository partRepository;
	
	public List<Marker> findPartByUserByProvince(String username, Long pkProvince){
		List<User> lsUsers = userRepository.findByUsername(username); 
		if(lsUsers != null && lsUsers.size() == 1){
			User user =  lsUsers.get(0);
			Province province = provinceRepository.findOne(pkProvince);
			List<Part> lsPart = partRepository.findPartByUserByProvince(user, province); 
			List<Marker> lsMarkers = new ArrayList<Marker>();
			for (Part part : lsPart) {
				lsMarkers.add(new Marker(part.getPk(), part.getName(), part.getLat(), part.getLng()));
			}
			return lsMarkers;
		}else{
			return null;
		}
	}
}
