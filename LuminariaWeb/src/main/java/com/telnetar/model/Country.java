package com.telnetar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="country", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c"),
	@NamedQuery(name="Country.findCountriesByUser", query="SELECT a FROM Country a, UserCountry b, User c WHERE a.pk = b.pk.pkCountry AND b.pk.pkUser = c.pk AND c.pk = :pkUser")
})

public class Country {
	@Id
	@Column(name="country_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pk;
	
	@Column(name="name", length=25)
	private String name;
	
	@Column(name="latitude")
	private Double lat;
	
	@Column(name="longitude")
	private Double lng;
	
	@Column(name="state")
	private Integer status;

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
}
