package com.telnetar.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telnetar.Util;
import com.telnetar.model.Luminaria;
import com.telnetar.model.LuminariaAuditoria;
import com.telnetar.services.repositories.LuminariaRepository;
import com.telnetar.services.repositories.NodoRepository;
import com.telnetar.services.repositories.UserRepository;
import com.telnetar.view.model.Marker;

@Service
public class LuminariaService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	NodoRepository nodoRepository;
	@Autowired
	LuminariaRepository luminariaRepository;
	@Autowired
	LuminariaAuditService luminariaAuditService;
	
	public List<Marker> findByPkNodo(Long pkNodo){
		List<Luminaria> lsLuminarias = luminariaRepository.findByPkNodo(pkNodo); 
		List<Marker> lsMarkers = new ArrayList<Marker>();
		for (Luminaria luminaria : lsLuminarias) {
			LuminariaAuditoria luminariaAuditoria = luminariaAuditService.obtenerUltimaAuditoria(luminaria.getHight(), luminaria.getLow());
			lsMarkers.add(
				new Marker(
					luminaria.getPk(), 
					luminaria.getLat(), 
					luminaria.getLng(), 
					luminaria.getDescripcion().trim(),
					new Double(luminariaAuditoria != null ? Util.getTemperature(luminariaAuditoria.getTempHight(), luminariaAuditoria.getTempLow()) : new Double(-100)),
					new Double(luminariaAuditoria != null ? Util.getTemperature(luminariaAuditoria.getLumiContextH(), luminariaAuditoria.getLumiContextL()) : new Double(-100)),
					luminariaAuditoria != null ? Util.getRealIntensity(luminariaAuditoria.getIntensity()) : new Integer(-100),
					luminariaAuditoria != null ? Util.formatDate(luminariaAuditoria.getFeRegistro(), "dd/MM/yyyy") : "",
					new Integer(5)
				)
			);
		}
		return lsMarkers;
	}

	public List<Luminaria> obtenerLuminariasNivelPais(String username) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasNivelPais(username);
		return lsLuminarias;
	}

	public List<Luminaria> obtenerLuminariasNivelProvincia(String username) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasNivelProvincia(username);
		return lsLuminarias;
	}

	public List<Luminaria> obtenerLuminariasNivelPartido(String username) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasNivelPartido(username);
		return lsLuminarias;
	}
	
	public List<Luminaria> obtenerLuminariasNivelLocalidad(String username) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasNivelLocalidad(username);
		return lsLuminarias;
	}
	
	public List<Luminaria> obtenerLuminariasNivelNodo(String username) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasNivelNodo(username);
		return lsLuminarias;
	}

	public List<Luminaria> obtenerLuminariasPorNodo(String username, Long pkNodo) {
		List<Luminaria> lsLuminarias = luminariaRepository.obtenerLuminariasPorNodo(username, pkNodo);
		return lsLuminarias;
	}

	public Luminaria findOne(Long pkLuminaria) {
		return luminariaRepository.findOne(pkLuminaria);
	}
	
	@Transactional
	public Luminaria save(Luminaria luminaria){
		luminaria = luminariaRepository.save(luminaria);
		luminaria.setNodo(nodoRepository.findOne(luminaria.getPkNodo()));
		return luminaria;
	}
}