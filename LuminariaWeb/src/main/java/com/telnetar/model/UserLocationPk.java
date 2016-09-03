package com.telnetar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserLocationPk implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="pk_user")
	private Long pkUser;
	@Column(name="pk_part")
	private Long pkPart;
	@Column(name="pk_location")
	private Long pkLocation;
	
	public Long getPkUser() {
		return pkUser;
	}
	public void setPkUser(Long pkUser) {
		this.pkUser = pkUser;
	}
	public Long getPkPart() {
		return pkPart;
	}
	public void setPkPart(Long pkPart) {
		this.pkPart = pkPart;
	}
	public Long getPkLocation() {
		return pkLocation;
	}
	public void setPkLocation(Long pkLocation) {
		this.pkLocation = pkLocation;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pkLocation == null) ? 0 : pkLocation.hashCode());
		result = prime * result + ((pkPart == null) ? 0 : pkPart.hashCode());
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
		UserLocationPk other = (UserLocationPk) obj;
		if (pkLocation == null) {
			if (other.pkLocation != null)
				return false;
		} else if (!pkLocation.equals(other.pkLocation))
			return false;
		if (pkPart == null) {
			if (other.pkPart != null)
				return false;
		} else if (!pkPart.equals(other.pkPart))
			return false;
		if (pkUser == null) {
			if (other.pkUser != null)
				return false;
		} else if (!pkUser.equals(other.pkUser))
			return false;
		return true;
	}
	
}