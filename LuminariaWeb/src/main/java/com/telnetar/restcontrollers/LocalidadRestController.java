package com.telnetar.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telnetar.model.Location;
import com.telnetar.services.LocationService;
import com.telnetar.view.model.DatatableData;

@RestController
@RequestMapping(value="/localidad")
public class LocalidadRestController {
	@Autowired
	private LocationService locationService;
	
	@RequestMapping(value="/localidadesPorPartido/{pkPart}")
	public ResponseEntity<DatatableData> localidadesPorPartido(@PathVariable Long pkPart){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Location> lsNodos = locationService.findLocationByUserByPart(username, pkPart);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsNodos, datatableData);
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}

	private void makeReturn(List<Location> lsLocations, DatatableData datatableData) {
		for (Location localidad : lsLocations) {
			List<String> data = new ArrayList<String>();
			data.add(localidad.getName());
			data.add(localidad.getUbicacion());
			data.add("<a data-toggle='modal' href='#localidadPopupPanel' class='btn btn-default btn-block' onclick='obtenerInfoLuminaria("+localidad.getPk()+")'>Administrar</a>");
			datatableData.getData().add(data);
		}
	}
	
	@RequestMapping(value="/infoLocalidad/{pkLocalidad}")
	public Location infoLocation(@PathVariable Long pkLocalidad){
		return locationService.findOne(pkLocalidad);
	}
}
