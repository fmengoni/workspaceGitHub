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
@Table(name="province", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Province.findAll", query="SELECT c FROM Country c"),
	@NamedQuery(
		name="Province.findProvinceByUserByCountry", 
		query="SELECT a FROM Province a, UserProvince b, User c WHERE a.pk = b.pk.pkProvince AND b.pk.pkUser = c.pk AND c.pk = :pkUser AND b.pk.pkCountry = :pkCountry")
})
public class Province {
	@Id
	@Column(name="province_id")
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
	@JoinColumn(name="country_id", referencedColumnName="country_id", insertable=false, updatable=false)
	private Country country;

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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}