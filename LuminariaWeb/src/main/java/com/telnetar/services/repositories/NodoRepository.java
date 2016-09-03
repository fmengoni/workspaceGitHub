package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Location;
import com.telnetar.model.Nodo;
import com.telnetar.model.User;

public interface NodoRepository extends CrudRepository<Nodo, Long>, NodoRepositoryCustom{
	public List<Nodo> findNodoByUserByLocation(User user, Location location);
}