package com.telnetar.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_systems database table.
 * 
 */
@Entity
@Table(name="user_systems", schema="sso")
@NamedQuery(name="UserSystem.findAll", query="SELECT u FROM UserSystem u")
public class UserSystem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pk")
	private Long pk;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="pk_user")
	private User user;

	//bi-directional many-to-one association to System
	@ManyToOne
	@JoinColumn(name="pk_system")
	private System system;

	public UserSystem() {
	}

	public Long getPk() {
		return this.pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public System getSystem() {
		return this.system;
	}

	public void setSystem(System system) {
		this.system = system;
	}

}