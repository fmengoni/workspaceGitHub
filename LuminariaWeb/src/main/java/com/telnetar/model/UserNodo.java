package com.telnetar.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="usernodos", schema="telnetar")
public class UserNodo implements Serializable{

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private UserNodoPk pk;
	
	public UserNodoPk getPk() {
		return pk;
	}
	public void setPk(UserNodoPk pk) {
		this.pk = pk;
	}
}