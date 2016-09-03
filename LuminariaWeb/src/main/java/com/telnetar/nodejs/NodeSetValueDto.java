package com.telnetar.nodejs;

import com.google.gson.Gson;

public class NodeSetValueDto {
    private String value;
    private String[] target;
    private Long roleId;
    private String token;
    
    public NodeSetValueDto() {
	super();
    }
    public NodeSetValueDto(Long roleId, String token, byte hightByte, byte lowByte, byte intensity, String[] target){
	Gson gson = new Gson();
	this.roleId = roleId;
	this.token = token;
	this.value = gson.toJson(new NodeSetValueDataDto(hightByte, lowByte, intensity));
	this.target = target;
    }
    public String[] getTarget() {
        return target;
    }
    public void setTarget(String[] target) {
        this.target = target;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}