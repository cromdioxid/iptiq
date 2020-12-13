package com.iptiq.exercise;

import java.util.UUID;

public class Provider {
	
	private UUID uuid;
	private boolean isAlive = true;
	
	public Provider() {
		this.uuid = UUID.randomUUID();
	}
	
	public String get() {
		return uuid.toString();
	}
	
	public boolean check() {
		return isAlive;
	}
	
	//for testing purposes
	public void setIsAlive(boolean value) {
		this.isAlive = value;
	}

}
