package com.telnetar.model;

import java.io.Serializable;

import javax.persistence.Column;

public class UserNodoPk implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="pk_user")
	private Long pkUser;
	@Column(name="pk_location")
	private Long pkLocation;
	@Column(name="pk_nodo")
	private Long pkNodo;
	
	public Long getPkUser() {
		return pkUser;
	}
	public void setPkUser(Long pkUser) {
		this.pkUser = pkUser;
	}
	public Long getPkLocation() {
		return pkLocation;
	}
	public void setPkLocation(Long pkLocation) {
		this.pkLocation = pkLocation;
	}
	public Long getPkNodo() {
		return pkNodo;
	}
	public void setPkNodo(Long pkNodo) {
		this.pkNodo = pkNodo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pkLocation == null) ? 0 : pkLocation.hashCode());
		result = prime * result + ((pkNodo == null) ? 0 : pkNodo.hashCode());
		result = prime * result + ((pkUser == null) ? 0 : pkUser.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserNodoPk other = (UserNodoPk) obj;
		if (pkLocation == null) {
			if (other.pkLocation != null)
				return false;
		} else if (!pkLocation.equals(other.pkLocation))
			return false;
		if (pkNodo == null) {
			if (other.pkNodo != null)
				return false;
		} else if (!pkNodo.equals(other.pkNodo))
			return false;
		if (pkUser == null) {
			if (other.pkUser != null)
				return false;
		} else if (!pkUser.equals(other.pkUser))
			return false;
		return true;
	}
	
}
