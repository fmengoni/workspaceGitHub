package com.telnetar.nodejs;

public class NodeSetValueDataDto {
    private byte hightByte;
    private byte lowByte;
    private byte intensity;
    
    public NodeSetValueDataDto() {
	super();
    }
    public NodeSetValueDataDto(byte hightByte, byte lowByte, byte intensity) {
	super();
	this.hightByte = hightByte;
	this.lowByte = lowByte;
	this.intensity = intensity;
    }
    public byte getHightByte() {
        return hightByte;
    }
    public void setHightByte(byte hightByte) {
        this.hightByte = hightByte;
    }
    public byte getLowByte() {
        return lowByte;
    }
    public void setLowByte(byte lowByte) {
        this.lowByte = lowByte;
    }
    public byte getIntensity() {
        return intensity;
    }
    public void setIntensity(byte intensity) {
        this.intensity = intensity;
    }
}
