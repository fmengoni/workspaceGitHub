package com.telnetar.view.model;

public class ViewUser {
	private Long pk;
	private String usuario;
	private String claveActual, claveNueva, repitaClaveNueva;
	private Boolean enabled;
	
	public Long getPk() {
		return pk;
	}
	public void setPk(Long pk) {
		this.pk = pk;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getClaveActual() {
		return claveActual;
	}
	public void setClaveActual(String claveActual) {
		this.claveActual = claveActual;
	}
	public String getClaveNueva() {
		return claveNueva;
	}
	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}
	public String getRepitaClaveNueva() {
		return repitaClaveNueva;
	}
	public void setRepitaClaveNueva(String repitaClaveNueva) {
		this.repitaClaveNueva = repitaClaveNueva;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
