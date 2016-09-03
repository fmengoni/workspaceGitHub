package com.telnetar;

public enum Roles {
	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN");
	
	private final String role;
	
	Roles(String name){
		this.role = name;
	}

	public String getRole() {
		return role;
	}
}