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
@Table(name="part", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Part.findAll", query="SELECT c FROM Part c"),
	@NamedQuery(name="Part.findPartByUserByProvince", query="SELECT a FROM Part a, UserPart b, User c WHERE a.pk = b.pk.pkPart AND b.pk.pkUser = c.pk AND c.pk = :pkUser AND b.pk.pkProvince = :pkProvince")
})

public class Part {
	@Id
	@Column(name="part_id")
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
	@JoinColumn(name="province_id", referencedColumnName="province_id", insertable=false, updatable=false)
	private Province province;

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

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}
}