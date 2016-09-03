package com.telnetar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="luminariasaudit", schema="telnetar")
@NamedQueries({
	@NamedQuery(name="LuminariaAuditoria.obtenerUltimaAuditoria", query="SELECT la FROM LuminariaAuditoria la WHERE la.highByte = :param1 AND la.lowByte = :param2 AND la.pk = (SELECT max(laa.pk) FROM LuminariaAuditoria laa WHERE laa.highByte = :param1 AND laa.lowByte = :param2)")
})
public class LuminariaAuditoria {
	@Id
	@Column(name="luminaria_audit_id")
	private Long pk;
	@Temporal(TemporalType.DATE)
	@Column(name="feRegistro")
	private Date feRegistro;
	@Column(name="temp_hight")
	private Integer tempHight;
	@Column(name="temp_low")
	private Integer tempLow;
	@Column(name="intensity")
	private Integer intensity;
	@Column(name="hight")
	private Integer highByte;
	@Column(name="low")
	private Integer lowByte;
	@Column(name="nodo_id")
	private String nodoId;
	@Column(name="lumi_context_h")
	private Integer lumiContextH;
	@Column(name="lumi_context_l")
	private Integer lumiContextL;
	
	public Long getPk() {
		return pk;
	}
	public void setPk(Long pk) {
		this.pk = pk;
	}
	public Date getFeRegistro() {
		return feRegistro;
	}
	public void setFeRegistro(Date feRegistro) {
		this.feRegistro = feRegistro;
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
	public Integer getIntensity() {
		return intensity;
	}
	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	public Integer getHighByte() {
		return highByte;
	}
	public void setHighByte(Integer highByte) {
		this.highByte = highByte;
	}
	public Integer getLowByte() {
		return lowByte;
	}
	public void setLowByte(Integer lowByte) {
		this.lowByte = lowByte;
	}
	public String getNodoId() {
		return nodoId;
	}
	public void setNodoId(String nodoId) {
		this.nodoId = nodoId;
	}
	public Integer getLumiContextH() {
		return lumiContextH;
	}
	public void setLumiContextH(Integer lumiContextH) {
		this.lumiContextH = lumiContextH;
	}
	public Integer getLumiContextL() {
		return lumiContextL;
	}
	public void setLumiContextL(Integer lumiContextL) {
		this.lumiContextL = lumiContextL;
	}
}