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

import com.telnetar.model.Nodo;
import com.telnetar.services.NodoService;
import com.telnetar.view.model.DatatableData;

@RestController
@RequestMapping(value="/nodos")
public class NodosRestController {
	@Autowired
	private NodoService nodoService;
	
	@RequestMapping(value="/nodosPorLocalidad/{pkLocalidad}")
	public ResponseEntity<DatatableData> nodosPorLocalidad(@PathVariable Long pkLocalidad){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Nodo> lsNodos = nodoService.findNodoByUserByLocation(username, pkLocalidad);
		DatatableData datatableData = new DatatableData();
		
		makeReturn(lsNodos, datatableData);
		
		return new ResponseEntity<DatatableData>(datatableData, HttpStatus.OK);
	}

	private void makeReturn(List<Nodo> lsNodos, DatatableData datatableData) {
		for (Nodo nodo : lsNodos) {
			List<String> data = new ArrayList<String>();
			data.add(nodo.getName());
			data.add(nodo.getUbicacion());
			data.add("<a data-toggle='modal' href='#nodoPopupPanel' class='btn btn-default btn-block' onclick='obtenerInfoNodo("+nodo.getPk()+")'>Administrar</a>");
			datatableData.getData().add(data);
		}
	}
	
	@RequestMapping(value="/infoNodo/{pkNodo}")
	public ResponseEntity<Nodo> infoNodo(@PathVariable Long pkNodo){
		return new ResponseEntity<>(nodoService.findOne(pkNodo), HttpStatus.OK);
	}
}
