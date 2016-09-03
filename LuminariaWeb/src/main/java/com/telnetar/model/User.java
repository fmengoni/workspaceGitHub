package com.telnetar.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users", schema="sso")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.changePassword", query="UPDATE User u SET u.password = :password WHERE u.pk = :pk"),
	@NamedQuery(name="User.update", query="UPDATE User u SET u.enabled = :enabled, u.password = :password, u.token = :token, u.tokenExpired = :tokenExpired WHERE u.pk = :pk")
})

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pk")
	private Long pk;
	@Column(name="enabled")
	private Integer enabled;
	@Column(name="password", length=255)
	private String password;
	@Column(name="username", length=45)
	private String username;
	@Column(name="email", length=255)
	private String email;
	@Column(name="token", length=100)
	private String token;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="token_expired")
	private Date tokenExpired;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<UserRole> userRoles;

	//bi-directional many-to-one association to UserSystem
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<UserSystem> userSystems;

	public User() {
	}

	public Long getPk() {
		return this.pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setUser(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setUser(null);

		return userRole;
	}

	public List<UserSystem> getUserSystems() {
		return this.userSystems;
	}

	public void setUserSystems(List<UserSystem> userSystems) {
		this.userSystems = userSystems;
	}

	public UserSystem addUserSystem(UserSystem userSystem) {
		getUserSystems().add(userSystem);
		userSystem.setUser(this);

		return userSystem;
	}

	public UserSystem removeUserSystem(UserSystem userSystem) {
		getUserSystems().remove(userSystem);
		userSystem.setUser(null);

		return userSystem;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Date tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

}