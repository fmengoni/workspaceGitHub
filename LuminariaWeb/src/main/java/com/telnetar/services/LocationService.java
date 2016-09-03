package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.Location;
import com.telnetar.model.Part;
import com.telnetar.model.User;
import com.telnetar.services.repositories.LocationRepository;
import com.telnetar.services.repositories.PartRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.Marker;

@Service
public class LocationService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PartRepository partRepository;
	@Autowired
	LocationRepository locationRepository;
	
	public List<Marker> findMarkersByUserByPart(String username, Long pkPart){
		List<Location> lsLocalidades = findLocationByUserByPart(username, pkPart);
		if(lsLocalidades != null){ 
			List<Marker> lsMarkers = new ArrayList<Marker>();
			for (Location location : lsLocalidades) {
				lsMarkers.add(new Marker(location.getPk(), location.getName(), location.getLat(), location.getLng()));
			}
			return lsMarkers;
		}else{
			return null;
		}
	}
	
	public List<Location> findLocationByUserByPart(String username, Long pkPart){
		List<User> lsUsers = userRepository.findByUsername(username); 
		if(lsUsers != null && lsUsers.size() == 1){
			User user =  lsUsers.get(0);
			Part part = partRepository.findOne(pkPart);
			List<Location> lsLocations = locationRepository.findLocationByUserByPart(user, part); 
			return lsLocations;
		}else{
			return null;
		}
	}

	public Location findOne(Long pkLocalidad) {
		return locationRepository.findOne(pkLocalidad);
	}
}
