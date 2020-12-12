package com.iptiq.exercise;

import java.util.HashMap;

public interface SelectionStrategy {
	
	public Provider get(HashMap<String, Provider> providersList);

}
