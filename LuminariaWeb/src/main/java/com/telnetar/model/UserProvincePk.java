package com.telnetar.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserProvincePk implements Serializable{

	private static final long serialVersionUID = 1L;
	@Column(name="pk_user")
	private Long pkUser;
	@Column(name="pk_country")
	private Long pkCountry;
	@Column(name="pk_province")
	private Long pkProvince;
	public Long getPkUser() {
		return pkUser;
	}
	public void setPkUser(Long pkUser) {
		this.pkUser = pkUser;
	}
	public Long getPkCountry() {
		return pkCountry;
	}
	public void setPkCountry(Long pkCountry) {
		this.pkCountry = pkCountry;
	}
	public Long getPkProvince() {
		return pkProvince;
	}
	public void setPkProvince(Long pkProvince) {
		this.pkProvince = pkProvince;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pkCountry == null) ? 0 : pkCountry.hashCode());
		result = prime * result + ((pkProvince == null) ? 0 : pkProvince.hashCode());
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
		UserProvincePk other = (UserProvincePk) obj;
		if (pkCountry == null) {
			if (other.pkCountry != null)
				return false;
		} else if (!pkCountry.equals(other.pkCountry))
			return false;
		if (pkProvince == null) {
			if (other.pkProvince != null)
				return false;
		} else if (!pkProvince.equals(other.pkProvince))
			return false;
		if (pkUser == null) {
			if (other.pkUser != null)
				return false;
		} else if (!pkUser.equals(other.pkUser))
			return false;
		return true;
	}
	
}
