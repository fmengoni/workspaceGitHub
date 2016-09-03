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
import javax.persistence.Transient;

import com.telnetar.Util;

@Entity
@Table(name="luminarias", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="Luminaria.findAll", query="SELECT l FROM Luminaria l"),
	@NamedQuery(name="Luminaria.findLuminariaByNodo", query="SELECT l FROM Luminaria l WHERE l.pkNodo = :pkNodo"),
	@NamedQuery(
		name="Luminaria.obtenerLuminariasPorUsuarioNivelPais", 
		query="select f from "
				+ "UserCountry a, "
				+ "UserProvince b, "
				+ "UserPart c, "
				+ "UserLocation d, "
				+ "UserNodo e, "
				+ "Luminaria f, "
				+ "User g "
			+ "WHERE "
				+ "a.pk.pkCountry = b.pk.pkCountry AND "
				+ "b.pk.pkProvince = c.pk.pkProvince AND "
				+ "c.pk.pkPart = d.pk.pkPart AND "
				+ "d.pk.pkLocation = e.pk.pkLocation AND "
				+ "e.pk.pkNodo = f.pkNodo AND "
				+ "g.pk = e.pk.pkUser AND "
				+ "g.username = :username"),
	@NamedQuery(
		name="Luminaria.obtenerLuminariasPorUsuarioNivelProvincia",
		query="select f from "
				+ "UserProvince b, "
				+ "UserPart c, "
				+ "UserLocation d, "
				+ "UserNodo e, "
				+ "Luminaria f, "
				+ "User g "
			+ "WHERE "
				+ "b.pk.pkProvince = c.pk.pkProvince AND "
				+ "c.pk.pkPart = d.pk.pkPart AND "
				+ "d.pk.pkLocation = e.pk.pkLocation AND "
				+ "e.pk.pkNodo = f.pkNodo AND "
				+ "g.pk = e.pk.pkUser AND "
				+ "g.username = :username"),
	@NamedQuery(
		name="Luminaria.obtenerLuminariasPorUsuarioNivelPartido",
		query="select f from "
				+ "UserPart c, "
				+ "UserLocation d, "
				+ "UserNodo e, "
				+ "Luminaria f, "
				+ "User g "
			+ "WHERE "
				+ "c.pk.pkPart = d.pk.pkPart AND "
				+ "d.pk.pkLocation = e.pk.pkLocation AND "
				+ "e.pk.pkNodo = f.pkNodo AND "
				+ "g.pk = e.pk.pkUser AND "
				+ "g.username = :username"),
	@NamedQuery(
			name="Luminaria.obtenerLuminariasPorUsuarioNivelLocalidad",
			query="select f from "
					+ "UserLocation d, "
					+ "UserNodo e, "
					+ "Luminaria f, "
					+ "User g "
				+ "WHERE "
					+ "d.pk.pkLocation = e.pk.pkLocation AND "
					+ "e.pk.pkNodo = f.pkNodo AND "
					+ "g.pk = e.pk.pkUser AND "
					+ "g.username = :username"),
	@NamedQuery(
			name="Luminaria.obtenerLuminariasPorUsuarioNivelNodo",
			query="select f from "
					+ "UserNodo e, "
					+ "Luminaria f, "
					+ "User g "
				+ "WHERE "
					+ "e.pk.pkNodo = f.pkNodo AND "
					+ "g.pk = e.pk.pkUser AND "
					+ "g.username = :username"),
	@NamedQuery(
		name="Luminaria.obtenerLuminariasPorUsuarioPorNodo",
		query="select f from "
					+ "UserNodo e, "
					+ "Luminaria f, "
					+ "User g "
				+ "WHERE "
					+ "e.pk.pkNodo = f.pkNodo AND "
					+ "g.pk = e.pk.pkUser AND "
					+ "g.username = :username AND "
					+ "e.pk.pkNodo = :pkNodo")
})
public class Luminaria {
	public Luminaria(){}
	public Luminaria(Long pkNodo, String descripcion, Integer hight, Integer low, Double lat, Double lng){
		this.pkNodo = pkNodo;
		this.descripcion = descripcion;
		this.hight = hight; 
		this.low = low;
		this.lat = lat;
		this.lng = lng;
	}
	@Id
	@Column(name="luminaria_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pk;
	
	@Column(name="location_group_id")
	private Long pkNodo;
	
	@ManyToOne
	@JoinColumn(name="location_group_id", referencedColumnName="location_group_id", insertable=false, updatable=false)
	private Nodo nodo;

	@Column(name="description", length=300)
	private String descripcion;
	
	@Column(name="hight")
	private Integer hight;
	
	@Column(name="low")
	private Integer low;
	
	@Column(name="latitude")
	private Double lat;
	
	@Column(name="longitude")
	private Double lng;
	
	@Column(name="intensity")
	private Integer intensidad;

	@Column(name="temp_hight")
	private Integer tempHight;
	
	@Column(name="temp_low")
	private Integer tempLow;
	
	@Column(name="state")
	private Integer status;
	
	@Transient
	private LuminariaAuditoria ultimaAuditoria;
	
	public String nodoId(){
		return 
			getNodo().getLocation().getPart().getProvince().getCountry().getPk().toString() + "|" +
			getNodo().getLocation().getPart().getProvince().getPk().toString() + "|" +
			getNodo().getLocation().getPart().getPk().toString() + "|" +
			getNodo().getLocation().getPk().toString() + "|" +
			getNodo().toString();
	}

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getHight() {
		return hight;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public Integer getLow() {
		return low;
	}

	public void setLow(Integer low) {
		this.low = low;
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

	public Integer getTempHight() {
		return tempHight;
	}

	public void setTempHight(Integer tempHight) {
		this.tempHight = tempHight;
	}

	public Integer getTempLow() {
		return tempLow;
	}

	public void setTempLow(Integer tempLow) {
		this.tempLow = tempLow;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIntensidad() {
		return intensidad;
	}

	public void setIntensidad(Integer intensidad) {
		this.intensidad = intensidad;
	}

	public LuminariaAuditoria getUltimaAuditoria() {
		return ultimaAuditoria;
	}

	public void setUltimaAuditoria(LuminariaAuditoria ultimaAuditoria) {
		this.ultimaAuditoria = ultimaAuditoria;
	}

	public Long getPkNodo() {
		return pkNodo;
	}

	public void setPkNodo(Long pkNodo) {
		this.pkNodo = pkNodo;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public void setNodo(Nodo nodo) {
		this.nodo = nodo;
	}
	
	public Long getTempLed(){
		return getUltimaAuditoria() != null && getUltimaAuditoria().getTempHight() != null && getUltimaAuditoria().getTempLow() != null ? Util.getTemperature(getUltimaAuditoria().getTempHight(), getUltimaAuditoria().getTempLow()) : new Long(-100);
	}
	
	public Long getTempLumi(){
		return getUltimaAuditoria() != null && getUltimaAuditoria().getTempHight() != null && getUltimaAuditoria().getTempLow() != null ? Util.getTemperature(getUltimaAuditoria().getLumiContextH(), getUltimaAuditoria().getLumiContextL()) : new Long(-100);
	}
	
	public Long getRealIntensity(){
		return getUltimaAuditoria() != null && getUltimaAuditoria().getIntensity() != null ? Util.getRealIntensity(getUltimaAuditoria().getIntensity()) : new Long(-100);
	}
	
	public String getUbicacion(Integer nivel){
		switch (nivel) {
		case 1:
			return getNodo().getLocation().getPart().getProvince().getCountry().getName() + " | " + getNodo().getLocation().getPart().getProvince().getName() + " | " + getNodo().getLocation().getPart().getName() + " | " + getNodo().getLocation().getName() + " | " + getNodo().getName();	
		case 2:
			return getNodo().getLocation().getPart().getProvince().getName() + " | " + getNodo().getLocation().getPart().getName() + " | " + getNodo().getLocation().getName() + " | " + getNodo().getName();
		case 3:
			return getNodo().getLocation().getPart().getName() + " | " + getNodo().getLocation().getName() + " | " + getNodo().getName();
		case 4:
			return getNodo().getLocation().getName() + " | " + getNodo().getName();
		default:
			return getNodo().getName();
		}
	}
}