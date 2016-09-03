package com.telnetar.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.telnetar.services.CountryService;
import com.telnetar.services.LocationService;
import com.telnetar.services.LuminariaService;
import com.telnetar.services.NodoService;
import com.telnetar.services.PartService;
import com.telnetar.services.ProvinceService;
import com.telnetar.view.model.Marker;

@RestController
public class DashboardRestController {
	@Autowired
	CountryService countryService;
	@Autowired
	ProvinceService provinceService;
	@Autowired
	PartService partService;
	@Autowired
	LocationService locationService;
	@Autowired
	NodoService nodoService;
	@Autowired
	LuminariaService luminariaService;
	
	@RequestMapping(value="/dashboardRest/countriesByUser", method=RequestMethod.POST)
	public ResponseEntity<List<Marker>> dashboardRestCountriesByUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Marker> lsMarkers = countryService.findCountriesByUser(username);
		return new ResponseEntity<List<Marker>>(lsMarkers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/dashboardRest/provinceByUserByCountry/{pk}", method=RequestMethod.POST)
	public ResponseEntity<List<Marker>> dashboardRestProvinceByUserByCountry(@PathVariable Long pk){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Marker> lsMarkers = provinceService.findProvinceByUserByCountry(username, pk);
		return new ResponseEntity<List<Marker>>(lsMarkers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/dashboardRest/partByUserByProvince/{pk}", method=RequestMethod.POST)
	public @ResponseBody List<Marker> dashboardRestPartByUserByProvince(@PathVariable Long pk){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Marker> lsMarkers = partService.findPartByUserByProvince(username, pk);
		return lsMarkers;
	}
	
	@RequestMapping(value="/dashboardRest/locationByUserByPart/{pk}", method=RequestMethod.POST)
	public @ResponseBody List<Marker> dashboardRestLocationByUserByPart(@PathVariable Long pk){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Marker> lsMarkers = locationService.findMarkersByUserByPart(username, pk);
		return lsMarkers;
	}
	
	@RequestMapping(value="/dashboardRest/nodoByUserByLocation/{pk}", method=RequestMethod.POST)
	public @ResponseBody List<Marker> dashboardRestNodoByUserByLocation(@PathVariable Long pk){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		List<Marker> lsMarkers = nodoService.findMarkersByUserByLocation(username, pk);
		return lsMarkers;
	}
	
	@RequestMapping(value="/dashboardRest/luminariaByNodo/{pk}", method=RequestMethod.POST)
	public @ResponseBody List<Marker> dashboardRestLuminariaByUserByNodo(@PathVariable Long pk){
		List<Marker> lsMarkers = luminariaService.findByPkNodo(pk);
		return lsMarkers;
	}
}
