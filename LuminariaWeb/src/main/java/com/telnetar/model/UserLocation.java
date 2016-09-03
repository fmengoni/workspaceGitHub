package com.telnetar.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="userlocation", schema="telnetar")
public class UserLocation {
	@EmbeddedId
	private UserLocationPk pk;

	public UserLocationPk getPk() {
		return pk;
	}

	public void setPk(UserLocationPk pk) {
		this.pk = pk;
	}
}