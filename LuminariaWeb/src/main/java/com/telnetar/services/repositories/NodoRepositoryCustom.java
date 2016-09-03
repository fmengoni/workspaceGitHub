package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Location;
import com.telnetar.model.Nodo;
import com.telnetar.model.User;

public interface NodoRepositoryCustom {
	public List<Nodo> findNodoByUserByLocation(User user, Location location);
}
