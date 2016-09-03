"use strict";

var mapa;
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

var xLumiTable;

var posicionarLuminariaValidator;

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
//	var contextRoot = /*[[@{/}]]*/ '';
//	return contextRoot;
   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
}

function addMarker(mapa, lat, lng, title, iwHtmlContentString){
	marker = new google.maps.Marker({
		position: {lat:lat,lng:lng},
		map: mapa,
		title: title
	});
	if(iwHtmlContentString != ''){
		var infowindow = new google.maps.InfoWindow({
		    content: iwHtmlContentString,
		    maxWidth: 250
		});
		marker.addListener('click', function() {
		    infowindow.open(mapa, marker);
		});
	}
}

//Sets the map on all markers in the array.
function setMapOnAll(mapa) {
  for (var i = 0; i < markers.length; i++) {
    markers[i].setMap(mapa);
  }
}

//Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
  setMapOnAll(null);
}

//Shows any markers currently in the array.
function showMarkers() {
  setMapOnAll(mapa);
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
			mapa.setCenter(initialCenterLatLng);
			level = 1;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addCountryMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(1);
			
			$('#mapsBreadcrumbs').html('');
			obtainLightLevelCountry();
			$('#posicionarLumiAgregarBtn').html('');
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addCountryMarker(mapa, lat, lng, title, pk, bounds){
	addMarker(mapa, lat, lng, title, '');
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
			mapa.setCenter(initialCenterLatLng);
			level = 2;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addProvinceMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(3);
			$("#mapsBreadcrumbs").html("<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>");
			
			obtainLightLevelProvince();
			$('#posicionarLumiAgregarBtn').html('');
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addProvinceMarker(mapa, lat, lng, title, pk, bounds){
	addMarker(mapa, lat, lng, title, '');
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
			mapa.setCenter(initialCenterLatLng);
			level = 3;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addPartMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(5);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>");
			
			obtainLightLevelPart();
			$('#posicionarLumiAgregarBtn').html('');
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addPartMarker(mapa, lat, lng, title, pk, bounds){
	addMarker(mapa, lat, lng, title, '');
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
			mapa.setCenter(initialCenterLatLng);
			level = 4;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addLocationMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(11);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>");
			obtainLightLevelLocation();
			$('#posicionarLumiAgregarBtn').html('');
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addLocationMarker(mapa, lat, lng, title, pk, bounds){
	addMarker(mapa, lat, lng, title, '');
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
			mapa.setCenter(initialCenterLatLng);
			level = 5;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addNodoMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(15);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainLocationByUserByPart()'>"+deLocalidad+"</a></li>");
			
			obtainLightLevelNodo();
			$('#posicionarLumiAgregarBtn').html('');
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addNodoMarker(mapa, lat, lng, title, pk, bounds){
	addMarker(mapa, lat, lng, title, '');
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
			mapa.setCenter(initialCenterLatLng);
			level = 6;
			
			var bounds = new google.maps.LatLngBounds();
			for (var i = 0; i < data.length; i++) {
				addLuminariaMarker(mapa, data[i].lat, data[i].lng, data[i].title, data[i].pk, data[i].tempLed, data[i].tempLumi, data[i].intensity, data[i].fechaRegistro, bounds);
			}
			mapa.fitBounds(bounds);
			mapa.setZoom(16);
			
			$("#mapsBreadcrumbs").html(
				"<li><a href='#' onclick='obtainCountryMarkerByUser()'>"+dePais+"</a></li>" +
				"<li><a href='#' onclick='obtainProvinceByUserByCountry()'>"+deProvincia+"</a></li>" +
				"<li><a href='#' onclick='obtainPartByUserByProvince()'>"+dePartido+"</a></li>" +
				"<li><a href='#' onclick='obtainLocationByUserByPart()'>"+deLocalidad+"</a></li>" +
				"<li class='current'><a href='#' onclick='obtainNodoByUserByLocation()'>"+deNodo+"</a></li>");
			
			obtainLightByNodo(pk);
			
			$('#posicionarLumiAgregarBtn').html("<button class='btn btn-success' onclick='agregarLuminaria()'>Agregar nueva luminaria</button>");
			hidePosicionarLumiDatos();
		},
		error: function(){
			alert('Se produjo un error inesperado');
		}
	});
}

function addLuminariaMarker(mapa, lat, lng, title, pk, tempLed, tempLumi, intensity, fechaRegistro, bounds){
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
	addMarker(mapa, lat, lng, title, html);
	bounds.extend(marker.position);
	
	markers.push(marker);
}

function agregarLuminaria(){
	$.ajax({
		url: getContextPath()+'/nodos/infoNodo/'+pkNodo,
		method: 'POST',
		success: function(data){
			marker = new google.maps.Marker({
				position: {lat:data.lat,lng:data.lng},
				map: mapa,
				draggable: true,
				animation: google.maps.Animation.DROP,
				title: 'Nueva Luminaria'
			});
			var infowindow = new google.maps.InfoWindow({
			    content: '<div class="row"><div class="col-xs-12"><h3>Posicione la luminaria y luego especifique el ID.</h3></div></div>',
			    maxWidth: 250
			});
			marker.addListener('click', function() {
			    infowindow.open(mapa, marker);
			});
			marker.addListener('drag', function(event){
				$('#posicionarLumiLat').val(event.latLng.lat());
				$('#posicionarLumiLng').val(event.latLng.lng());
			});
			markers.push(marker);
			$('#posicionarLumiAgregarBtn').html('');
			$('#posicionarLumiDatosLumi').show(1000);
			$('#posicionarLumiLat').val(data.lat);
			$('#posicionarLumiLng').val(data.lng);
			$('#posicionarLumiSuccessMsg').hide();
		},
		error: function(){
			
		}
	});
}

function agregarLuminariaBtn(){
	$('#posicionarLumiErrorMsg').hide(500);
	$('#posicionarLumiSuccessMsg').hide(500);
	if($('#posicionarLumiHight').val() == ""){
		$('#posicionarLumiErrorMsg').show(500);
		$('#posicionarLumiErrorMsgText').text("Debe agregar el valor Byte Alto");
	}else if($('#posicionarLumiLow').val() == ""){
		$('#posicionarLumiErrorMsg').show(500);
		$('#posicionarLumiErrorMsgText').text("Debe agregar el valor Byte Bajo");
	}else if($('#posicionarLumiDesc').val() == ""){
		$('#posicionarLumiErrorMsg').show(500);
		$('#posicionarLumiErrorMsgText').text("Debe agregar una descripci&oacute;n");
	}else{
		$.ajax({
			url: getContextPath()+'/luminarias/agregar/'+pkNodo+'/'+$('#posicionarLumiHight').val()+'/'+$('#posicionarLumiLow').val()+'/'+$('#posicionarLumiLat').val()+'/'+$('#posicionarLumiLng').val()+'/'+$('#posicionarLumiDesc').val(),
			method: 'PUT',
			success: function(data){
				marker.setDraggable(false);
				google.maps.event.clearListeners(marker, 'click');
				marker.setTitle('Luminaria agregada recientemente');
				var html = 
					'<div class="row">'+
						'<div class="col-xs-7">Temperatura del Led:</div>' +
						'<div class="col-xs-5">Sin registro</div>' +
					'</div>'+
					'<div class="row">'+
						'<div class="col-xs-7">Temperatura de la luminaria:</div>' +
						'<div class="col-xs-5">Sin registro</div>' +
					'</div>'+
					'<div class="row">'+
						'<div class="col-xs-7">Intensidad:</div>' +
						'<div class="col-xs-5">Sin registro</div>' +
					'</div>'+
					'<div class="row">'+
						'<div class="col-xs-7">Fecha de la auditoria:</div>' +
						'<div class="col-xs-5">Sin registro</div>' +
					'</div>';
				var infowindow = new google.maps.InfoWindow({
				    content: html,
				    maxWidth: 250
				});
				marker.addListener('click', function() {
				    infowindow.open(mapa, marker);
				});
				xLumiTable.row.add([data.hight+'-'+data.low, data.nodo.name]).draw(false);
				$('#posicionarLumiDatosLumi').hide(1000);
				$('#posicionarLumiErrorMsg').hide();
				$('#posicionarLumiSuccessMsg').show(500);
				$('#posicionarLumiSuccessMsgText').text('La luminaria fue configurada correctamente');
			}, error: function(data){
				$('#posicionarLumiSuccessMsg').hide();
				$('#posicionarLumiErrorMsg').show(500);
				$('#posicionarLumiErrorMsgText').text(data);
			}
		});
	}
}

$(document).ready(function(){
	var mapOptions = {
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			mapTypeControl: false,
			scaleControl: false,
			streetViewControl: false,
			rotateControl: false
	}
	mapa = new google.maps.Map(document.getElementById('gmap_markers'), mapOptions);
	
	xLumiTable = $('#posicionarLumiTable').DataTable({
    });
	
	obtainCountryMarkerByUser();
	
	hidePosicionarLumiDatos();
	
	$.extend( $.validator.defaults, {
		invalidHandler: function(form, validator) {
			var errors = validator.numberOfInvalids();
			if (errors) {
				var message = errors == 1
				? 'Existe un error, está marcado en color rojo.'
				: 'Existen ' + errors + ' errores. Están marcados en color rojo.';
				noty({
					text: message,
					type: 'error',
					timeout: 2000
				});
			}
		}
	});
	posicionarLuminariaValidator = $("#posicionarLuminariaForm").validate();
});

function hidePosicionarLumiDatos(){
	$('#posicionarLumiDatosLumi').hide();
	$('#posicionarLumiSuccessMsg').hide();
	$('#posicionarLumiErrorMsg').hide();
}

function obtainLightLevelCountry(){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelPais/1").load();
}

function obtainLightLevelProvince(){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelProvincia/1").load();
}

function obtainLightLevelPart(){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelPartido/1").load();
}

function obtainLightLevelLocation(){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelLocalidad/1").load();
}

function obtainLightLevelNodo(){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasNivelNodo/1").load();
}

function obtainLightByNodo(pk){
	xLumiTable.ajax.url(getContextPath()+"/luminarias/luminariasPorNodo/"+pk+"/1").load();
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

function obtenerInfoLuminaria(pkLocalidad){
	$.ajax({
		url: getContextPath()+'/localidad/infoLocalidad/'+pkLocalidad,
		method: 'POST',
		success: function(data){
			$('#localidadDescPopupPanel').text(data.name);
		},
		error: function(){
			
		}
	});
}