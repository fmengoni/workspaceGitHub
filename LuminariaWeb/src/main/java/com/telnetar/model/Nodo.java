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
@Table(name="locationgroup", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Nodo.findAll", query="SELECT n FROM Nodo n"),
	@NamedQuery(name="Nodo.findNodoByUserByLocation", query="SELECT a FROM Nodo a, UserNodo b, User c WHERE a.pk = b.pk.pkNodo AND b.pk.pkUser = c.pk AND c.pk = :pkUser AND b.pk.pkLocation = :pkLocation")
})
public class Nodo {
	@Id
	@Column(name="location_group_id")
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
	@JoinColumn(name="location_id", referencedColumnName="location_id", insertable=false, updatable=false)
	private Location location;

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

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getUbicacion() {
		return getLocation().getPart().getProvince().getCountry().getName() + "|" + getLocation().getPart().getProvince().getName() + "|" + getLocation().getPart().getName() + "|" + getLocation().getName();
	}
}