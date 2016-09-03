package com.telnetar.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="usercountry", schema="telnetar")
public class UserCountry {
	@EmbeddedId
	private UserCountryPk pk;

	public UserCountryPk getPk() {
		return pk;
	}

	public void setPk(UserCountryPk pk) {
		this.pk = pk;
	}
}
