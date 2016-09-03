package com.telnetar.services.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.telnetar.model.Luminaria;

public interface LuminariaRepository extends CrudRepository<Luminaria, Long>, LuminariaRepositoryCustom{
	public List<Luminaria> findByPkNodo(Long pkNodo);

	public List<Luminaria> obtenerLuminariasNivelPais(String username);

	public List<Luminaria> obtenerLuminariasNivelProvincia(String username);

	public List<Luminaria> obtenerLuminariasNivelPartido(String username);
	
	public List<Luminaria> obtenerLuminariasNivelLocalidad(String username);
	
	public List<Luminaria> obtenerLuminariasNivelNodo(String username);

	public List<Luminaria> obtenerLuminariasPorNodo(String username, Long pkNodo);
}
