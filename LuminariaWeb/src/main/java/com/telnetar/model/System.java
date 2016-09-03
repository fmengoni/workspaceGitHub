package com.telnetar.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the systems database table.
 * 
 */
@Entity
@Table(name="systems", schema="sso")
@NamedQuery(name="System.findAll", query="SELECT s FROM System s")
public class System implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pk;

	private String description;

	//bi-directional many-to-one association to UserSystem
	@OneToMany(mappedBy="system")
	private List<UserSystem> userSystems;

	public System() {
	}

	public Long getPk() {
		return this.pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserSystem> getUserSystems() {
		return this.userSystems;
	}

	public void setUserSystems(List<UserSystem> userSystems) {
		this.userSystems = userSystems;
	}

	public UserSystem addUserSystem(UserSystem userSystem) {
		getUserSystems().add(userSystem);
		userSystem.setSystem(this);

		return userSystem;
	}

	public UserSystem removeUserSystem(UserSystem userSystem) {
		getUserSystems().remove(userSystem);
		userSystem.setSystem(null);

		return userSystem;
	}

}