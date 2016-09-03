package com.telnetar.view.model;

import com.telnetar.Constants;

public class Marker {
	private Long pk;
	private Double lat;
	private Double lng;
	private String title;
	private Double tempLed;
	private Double tempLumi;
	private Integer intensity;
	private Integer level;
	private String fechaRegistro;
	
	public Marker(){}
	public Marker(Long pk, Double lat, Double lng, String title, Double tempLed, Double tempLumi, Integer intensity, String fechaRegistro, Integer level) {
		super();
		this.pk = pk;
		this.lat = lat;
		this.lng = lng;
		this.title = title;
		this.tempLed = tempLed;
		this.tempLumi = tempLumi;
		this.intensity = intensity;
		this.fechaRegistro = fechaRegistro;
		this.level = level;
	}
	public Marker(Long pk, String name, Double lat, Double lng) {
		this.pk = pk;
		this.lat = lat;
		this.lng = lng;
		this.title = name;
		this.level = Constants.LEVEL_COUNTRY;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getTempLed() {
		return tempLed;
	}
	public void setTempLed(Double tempLed) {
		this.tempLed = tempLed;
	}
	public Double getTempLumi() {
		return tempLumi;
	}
	public void setTempLumi(Double tempLumi) {
		this.tempLumi = tempLumi;
	}
	public Integer getIntensity() {
		return intensity;
	}
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getPk() {
		return pk;
	}
	public void setPk(Long pk) {
		this.pk = pk;
	}
	public String getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}