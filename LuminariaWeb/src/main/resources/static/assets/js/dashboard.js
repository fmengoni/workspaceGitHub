"use strict";

var map;
var markers = [];
var marker;
var initialCenterLatLng = {lat: 15.784265, lng: -33.545173};
var level;

var pkPais;
var dePais;
var pkProvincia;
var deProvincia;
var pkPartido;
var dePartido;
var pkLocalidad;
var deLocalidad;
var pkNodo;
var deNodo;
var pkLumi;

var lumiTable;
var nodoTable;
var localidadTable;

function setPkDePais(pPkPais, pDePais){
	pkPais = pPkPais;
	dePais = pDePais;
	setPkDeProvincia(null, null);
}

function setPkDeProvincia(pPkProvincia, pDeProvincia){
	pkProvincia = pPkProvincia;
	deProvincia = pDeProvincia;
	setPkDePartido(null, null);
}

function setPkDePartido(pPkPartido, pDePartido){
	pkPartido = pPkPartido;
	dePartido = pDePartido;
	setPkDeLocalidad(null, null);
}

function setPkDeLocalidad(pPkLocalidad, pDeLocalidad){
	pkLocalidad = pPkLocalidad;
	deLocalidad = pDeLocalidad;
	setPkDeNodo(null, null);
}

function setPkDeNodo(pPkNodo, pDeNodo){
	pkNodo = pPkNodo;
	deNodo = pDeNodo;
}

function getContextPath() {
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function addMarker(map, lat, lng, title, iwHtmlContentString){
	marker = new google.maps.Marker({
		position: {lat:lat,lng:lng},
		map: map,
		title: title
	});
	if(iwHtmlContentString != ''){
		var infowindow = new google.maps.InfoWindow({
		    content: iwHtmlContentString,
		    maxWidth: 250
		});
		marker.addListener('click', function() {
		    infowindow.open(map, marker);
		});
	}
}

//Sets the map on all markers in the array.
function setMapOnAll(map) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(map);
  }
}

//Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setMapOnAll(null);
}

//Shows any markers currently in the array.
function showMarkers() {
  setMapOnAll(map);
}

//Deletes all markers in the array by removing references to them.
function deleteMarkers() {
  clearMarkers();
  markers = [];
}

function obtainCountryMarkerByUser() {
	setPkDePais(null, null);
	$.ajax({
		url: getContextPath()+'/dashboardRest/countriesByUser',
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 1;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addCountryMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(1);
			
			$('#mapsBreadcrumbs').html('');
			obtainLightLevelCountry();
			
			$("#nodoTableDiv").html('');
			$("#localidadTableDiv").html('');
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addCountryMarker(map, lat, lng, title, pk, bounds){
	addMarker(map, lat, lng, title, '');
	bounds.extend(marker.position);
	google.maps.event.addListener(marker, 'dblclick', function(event) {
		setPkDePais(pk, title);
		obtainProvinceByUserByCountry();
	});
	markers.push(marker);
}

function obtainProvinceByUserByCountry(){
	setPkDeProvincia(null, null);
	$.ajax({
		url: getContextPath()+'/dashboardRest/provinceByUserByCountry/'+pkPais,
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 2;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addProvinceMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(3);
			$("#mapsBreadcrumbs").html("<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>");
			
			obtainLightLevelProvince();
			
			$("#nodoTableDiv").html('');
			$("#localidadTableDiv").html('');
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addProvinceMarker(map, lat, lng, title, pk, bounds){
	addMarker(map, lat, lng, title, '');
	bounds.extend(marker.position);
	google.maps.event.addListener(marker, 'dblclick', function(event) {
		setPkDeProvincia(pk, title);
		obtainPartByUserByProvince();
	});
	markers.push(marker);
}

function obtainPartByUserByProvince(){
	setPkDePartido(null, null);
	$.ajax({
		url: getContextPath()+'/dashboardRest/partByUserByProvince/'+pkProvincia,
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 3;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addPartMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(5);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>");
			
			obtainLightLevelPart();
			
			$("#nodoTableDiv").html('');
			$("#localidadTableDiv").html('');
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addPartMarker(map, lat, lng, title, pk, bounds){
	addMarker(map, lat, lng, title, '');
	bounds.extend(marker.position);
	google.maps.event.addListener(marker, 'dblclick', function(event) {
		setPkDePartido(pk, title);
		obtainLocationByUserByPart();
	});
	markers.push(marker);
}

function obtainLocationByUserByPart(){
	setPkDeLocalidad(null, null);
	$.ajax({
		url: getContextPath()+'/dashboardRest/locationByUserByPart/'+pkPartido,
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 4;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addLocationMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(11);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>");
			obtainLightLevelLocation();
			
			$("#nodoTableDiv").html('');
			$("#localidadTableDiv").html(
				"<div><table id='localidadTable' class='display' cellspacing='0' width='100%'>" +
					"<thead>" +
						"<tr>" +
							"<th>Descripci&oacute;n</th>" +
							"<th>Ubicaci&oacute;n</th>" +
							"<th></th>" +
						"</tr>"+
			        "</thead>"+
			        "<tfoot>"+
			            "<tr>"+
			                "<th>Descripci&oacute;n</th>"+
			                "<th>Ubicación</th>"+
			                "<th></th>"+
			            "</tr>"+
			        "</tfoot>"+
			        "<tbody></tbody>"+
			    "</table></div>" +
			    "<div><hr/></div>"
			);
			localidadTable = $('#localidadTable').DataTable({
				responsive: true
			});
			localidadTable.ajax.url("/localidad/localidadesPorPartido/"+pkPartido).load();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addLocationMarker(map, lat, lng, title, pk, bounds){
	addMarker(map, lat, lng, title, '');
	bounds.extend(marker.position);
	google.maps.event.addListener(marker, 'dblclick', function(event) {
		setPkDeLocalidad(pk, title);
		obtainNodoByUserByLocation();
	});
	
	markers.push(marker);
}

function obtainNodoByUserByLocation(){
	setPkDeNodo(null, null);
	$.ajax({
		url: getContextPath()+'/dashboardRest/nodoByUserByLocation/'+pkLocalidad,
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 5;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addNodoMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(15);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainLocationByUserByPart()'>"+deLocalidad+"</a></li>");
			
			obtainLightLevelNodo();
			
			$("#nodoTableDiv").html(
				"<div><table id='nodoTable' class='display' cellspacing='0' width='100%'>" +
					"<thead>" +
						"<tr>" +
							"<th>Descripci&oacute;n</th>" +
							"<th>Ubicaci&oacute;n</th>" +
							"<th></th>" +
						"</tr>"+
			        "</thead>"+
			        "<tfoot>"+
			            "<tr>"+
			                "<th>Descripci&oacute;n</th>"+
			                "<th>Ubicación</th>"+
			                "<th></th>"+
			            "</tr>"+
			        "</tfoot>"+
			        "<tbody></tbody>"+
			    "</table></div>" +
			    "<div><hr/></div>"
			);
			nodoTable = $('#nodoTable').DataTable({
				responsive: true
			});
			nodoTable.ajax.url("/nodos/nodosPorLocalidad/"+pkLocalidad).load();
			
			$("#localidadTableDiv").html('');
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addNodoMarker(map, lat, lng, title, pk, bounds){
	addMarker(map, lat, lng, title, '');
	bounds.extend(marker.position);
	google.maps.event.addListener(marker, 'dblclick', function(event) {
		setPkDeNodo(pk, title);
		obtainLuminariaByNodo(pk, title);
	});
	markers.push(marker);
}

function obtainLuminariaByNodo(pk, title){
	$.ajax({
		url: getContextPath()+'/dashboardRest/luminariaByNodo/'+pk,
		method: 'POST',
		success: function(data){
			deleteMarkers();
			map.setCenter(initialCenterLatLng);
			level = 6;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addLuminariaMarker(map, data[i].lat, data[i].lng, data[i].title, data[i].pk, data[i].tempLed, data[i].tempLumi, data[i].intensity, data[i].fechaRegistro, bounds);
			}
			map.fitBounds(bounds);
			map.setZoom(16);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>" +
				"<li><a href='#' onclick='obtainLocationByUserByPart()'>"+deLocalidad+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainNodoByUserByLocation()'>"+deNodo+"</a></li>");
			
			obtainLightByNodo(pk);
			
			$("#nodoTableDiv").html('');
			$("#localidadTableDiv").html('');
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addLuminariaMarker(map, lat, lng, title, pk, tempLed, tempLumi, intensity, fechaRegistro, bounds){
	var html = 
		'<div class="row">'+
			'<div class="col-xs-7">Temperatura del Led:</div>' +
			'<div class="col-xs-5">'+tempLed+'</div>' +
		'</div>'+
		'<div class="row">'+
			'<div class="col-xs-7">Temperatura de la luminaria:</div>' +
			'<div class="col-xs-5">'+tempLumi+'</div>' +
		'</div>'+
		'<div class="row">'+
			'<div class="col-xs-7">Intensidad:</div>' +
			'<div class="col-xs-5">'+intensity+'</div>' +
		'</div>'+
		'<div class="row">'+
			'<div class="col-xs-7">Fecha de la auditoria:</div>' +
			'<div class="col-xs-5">'+fechaRegistro+'</div>' +
		'</div>';
	addMarker(map, lat, lng, title, html);
	bounds.extend(marker.position);
	
	markers.push(marker);
}

$(document).ready(function(){
	var mapOptions = {
			mapTypeId: google.maps.MapTypeId.HYBRID,
			mapTypeControl: false,
			scaleControl: false,
			streetViewControl: false,
			rotateControl: false
	}
	map = new google.maps.Map(document.getElementById('gmap_markers'), mapOptions);
	
	lumiTable = $('#lumiTable').DataTable({
		responsive: true,
    	"createdRow": function ( row, data, index ) {
    		var tempLed = data[4].replace(/[\$,]/g, '') * 1;
    		var tempLumi = data[5].replace(/[\$,]/g, '') * 1;
            if ( tempLed <= 60 ) {
                $('td', row).eq(4).addClass('success');
            }
            if(tempLed > 60){
            	$('td', row).eq(4).addClass('warning');
            }
            if(tempLed > 65){
            	$('td', row).eq(4).addClass('error');
            }
            if ( tempLumi <= 60 ) {
                $('td', row).eq(5).addClass('success');
            }
            if(tempLumi > 60){
            	$('td', row).eq(5).addClass('warning');
            }
            if(tempLumi > 65){
            	$('td', row).eq(5).addClass('error');
            }
        } 
    });
	
	obtainCountryMarkerByUser();
	
	$( '#lumiIntensidadSlider' ).slider({
		range: 'min',
		min: 0,
		max: 100,
		step: 5,
		animation:"slow",
		slide: function( event, ui ) {
			$( '#lumiIntensidad' ).text( '$' + ui.value );
		},
		change: function( event, ui ) {
			setIntensityViaNodeJs();
//			alert('Falta desarrollar el llamado para setear la intensidad de la luminaria. Valor elegido: ' + ui.value);
		}
	});
	$('#lumiIntensidad').text('$' + $('#lumiIntensidadSlider').slider('value'));
});

function setIntensityViaNodeJs(){
	$.ajax({
		url: getContextPath()+'/luminarias/setIntensity',
		method: 'POST',
		data:{
			"pkLumi":pkLumi,
			"intensity":$('#lumiIntensidadSlider').slider('value')
		},
		success: function(data){
			
		}, error: function(data){
			
		}
	});
}

function obtainLightLevelCountry(){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelPais/0").load();
}

function obtainLightLevelProvince(){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelProvincia/0").load();
}

function obtainLightLevelPart(){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelPartido/0").load();
}

function obtainLightLevelLocation(){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelLocalidad/0").load();
}

function obtainLightLevelNodo(){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelNodo/0").load();
}

function obtainLightByNodo(pk){
	lumiTable.ajax.url(getContextPath()+"/luminarias/luminariasPorNodo/"+pk+"/0").load();
}

function obtenerInfoLuminaria(pkLuminaria){
	$.ajax({
		url: getContextPath()+'/luminarias/infoLuminaria/'+pkLuminaria,
		method: 'POST',
		success: function(data){
			$('#hightLow').text(data.hight + "-" + data.low);
			var tempLed = data.tempLed;
			var tempLumi = data.tempLumi;
			var tempLedHtml = '';
			var tempLumiHtml = '';
			pkLumi = data.pk;
			if(tempLed < 60){
				tempLedHtml = "<div class='progress-bar progress-bar-success' style='width: "+tempLed+"%'></div>";
			}else if(tempLed >= 60 && tempLed < 65){
				var diff = tempLed-60;
				tempLedHtml = 
					"<div class='progress-bar progress-bar-success' style='width: 60%'></div><div class='progress-bar progress-bar-warning' style='width: "+diff+"%'></div>";
				
			}else{
				var diff = tempLed-65;
				tempLedHtml = 
					"<div class='progress-bar progress-bar-success' style='width: 60%'></div><div class='progress-bar progress-bar-warning' style='width: 5%'></div><div class='progress-bar progress-bar-danger' style='width: "+diff+"%'></div>";
			}
			$('#lumiTempLed').html(tempLedHtml);
			if(tempLumi < 60){
				tempLumiHtml = "<div class='progress-bar progress-bar-success' style='width: "+tempLumi+"%'></div>";
			}else if(tempLumi >= 60 && tempLumi < 65){
				var diff = tempLumi - 60;
				tempLumiHtml = 
					"<div class='progress-bar progress-bar-success' style='width: 60%'></div><div class='progress-bar progress-bar-warning' style='width: "+diff+"%'></div>";
			}else{
				var diff = tempLumi - 65;
				tempLumiHtml = 
					"<div class='progress-bar progress-bar-success' style='width: 60%'></div><div class='progress-bar progress-bar-warning' style='width: 5%'></div><div class='progress-bar progress-bar-danger' style='width: "+diff+"%'></div>";
			}
			$('#lumiTempLumi').html(tempLumiHtml);
			$( "#lumiIntensidadSlider" ).slider( "value", data.realIntensity );
			$('#lumiIntensidad').text('$' + $('#lumiIntensidadSlider').slider('value'));
		},
		error: function(){
			
		}
	});
}

function obtenerInfoNodo(pkNodo){
	$.ajax({
		url: getContextPath()+'/nodos/infoNodo/'+pkNodo,
		method: 'POST',
		success: function(data){
			$('#nodoDescPopupPanel').text(data.name);
		},
		error: function(){
			
		}
	});
}

//function obtenerInfoLuminaria(pkLocalidad){
//	$.ajax({
//		url: getContextPath()+'/localidad/infoLocalidad/'+pkLocalidad,
//		method: 'POST',
//		success: function(data){
//			$('#localidadDescPopupPanel').text(data.name);
//		},
//		error: function(){
//			
//		}
//	});
//}