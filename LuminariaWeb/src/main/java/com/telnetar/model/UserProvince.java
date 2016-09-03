package com.telnetar.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="userprovince", schema="telnetar")
public class UserProvince {
	@EmbeddedId
	private UserProvincePk pk;

	public UserProvincePk getPk() {
		return pk;
	}

	public void setPk(UserProvincePk pk) {
		this.pk = pk;
	}
}