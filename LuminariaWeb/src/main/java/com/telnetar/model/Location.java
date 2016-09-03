package com.telnetar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="location", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Location.findAll", query="SELECT c FROM Location c"),
	@NamedQuery(name="Location.findLocationByUserByPart", query="SELECT a FROM Location a, UserLocation b, User c WHERE a.pk = b.pk.pkLocation AND b.pk.pkUser = c.pk AND c.pk = :pkUser AND b.pk.pkPart = :pkPart")
})
public class Location {
	@Id
	@Column(name="location_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pk;
	
	@Column(name="name")
	private String name;
	
	@Column(name="latitude")
	private Double lat;
	
	@Column(name="longitude")
	private Double lng;
	
	@Column(name="state")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name="part_id", referencedColumnName="part_id", insertable=false, updatable=false)
	private Part part;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getUbicacion() {
		return getPart().getProvince().getCountry().getName() + " | " + getPart().getProvince().getName() + " | " + getPart().getName();
	}
}