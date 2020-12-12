package com.iptiq.exercise;

import java.util.UUID;

public class Provider {
	
	private UUID uuid;
	
	public Provider() {
		this.uuid = UUID.randomUUID();
	}
	
	public String get() {
		return uuid.toString();
	}

}
