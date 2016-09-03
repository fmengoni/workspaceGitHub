package com.telnetar.services.repositories;

import java.util.List;

import com.telnetar.model.Luminaria;

public interface LuminariaRepositoryCustom {
	public List<Luminaria> obtenerLuminariasNivelPais(String username);
	public List<Luminaria> obtenerLuminariasNivelProvincia(String username);
	public List<Luminaria> obtenerLuminariasNivelPartido(String username);
	public List<Luminaria> obtenerLuminariasNivelLocalidad(String username);
	public List<Luminaria> obtenerLuminariasNivelNodo(String username);
	public List<Luminaria> obtenerLuminariasPorNodo(String username, Long pkNodo);
}
