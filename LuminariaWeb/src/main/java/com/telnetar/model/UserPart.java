package com.telnetar.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="userpart", schema="telnetar")
public class UserPart {
	@EmbeddedId
	private UserPartPk pk;

	public UserPartPk getPk() {
		return pk;
	}

	public void setPk(UserPartPk pk) {
		this.pk = pk;
	}
}