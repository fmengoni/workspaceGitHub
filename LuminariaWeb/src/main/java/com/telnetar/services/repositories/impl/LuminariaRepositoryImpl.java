package com.telnetar.services.repositories.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.telnetar.model.Luminaria;
import com.telnetar.services.repositories.LuminariaRepositoryCustom;
import com.telnetar.services.repositories.UserRepository;

public class LuminariaRepositoryImpl implements LuminariaRepositoryCustom{
	@Autowired
	EntityManager em;
	@Autowired
	UserRepository userRepository;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasNivelPais(String username) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioNivelPais")
			.setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasNivelProvincia(String username) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioNivelProvincia")
			.setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasNivelPartido(String username) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioNivelPartido")
			.setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasNivelLocalidad(String username) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioNivelLocalidad")
			.setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasNivelNodo(String username) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioNivelNodo")
			.setParameter("username", username);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Luminaria> obtenerLuminariasPorNodo(String username, Long pkNodo) {
		Query query = em.createNamedQuery("Luminaria.obtenerLuminariasPorUsuarioPorNodo")
			.setParameter("username", username)
			.setParameter("pkNodo", pkNodo);
		return query.getResultList();
	}
}