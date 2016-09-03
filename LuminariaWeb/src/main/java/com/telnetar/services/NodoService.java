package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.model.Location;
import com.telnetar.model.Nodo;
import com.telnetar.model.User;
import com.telnetar.services.repositories.LocationRepository;
import com.telnetar.services.repositories.NodoRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.Marker;

@Service
public class NodoService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	NodoRepository nodoRepository;
	
	public List<Marker> findMarkersByUserByLocation(String username, Long pkLocation){
		List<Nodo> lsNodos = findNodoByUserByLocation(username, pkLocation);
		if(lsNodos != null){
			List<Marker> lsMarkers = new ArrayList<Marker>();
			for (Nodo nodo : lsNodos) {
				lsMarkers.add(new Marker(nodo.getPk(), nodo.getName(), nodo.getLat(), nodo.getLng()));
			}
			return lsMarkers;
		}else{
			return null;
		}
	}
	
	public List<Nodo> findNodoByUserByLocation(String username, Long pkLocation){
		List<User> lsUsers = userRepository.findByUsername(username); 
		if(lsUsers != null && lsUsers.size() == 1){
			User user =  lsUsers.get(0);
			Location location = locationRepository.findOne(pkLocation);
			List<Nodo> lsNodos = nodoRepository.findNodoByUserByLocation(user, location);
			return lsNodos;
		}else{
			return null;
		}
	}

	public Nodo findOne(Long pkNodo) {
		return nodoRepository.findOne(pkNodo);
	}
}