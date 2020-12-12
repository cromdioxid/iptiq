package com.iptiq.exercise;

import java.util.HashMap;
import java.util.Random;

public class LoadBalancer {
	
	private HashMap<String, Provider> providersMap = new HashMap<String, Provider>();
	
	public boolean registerProvider(Provider provider) {
		if (providersMap.size() < 10) {
			providersMap.put(provider.get(), provider);
			return true;
		} else {
			//Return false if the max number of
			// providers is reached
			return false;
		}
	}
	
	public String get() {
		String[] keys = providersMap.keySet().toArray(new String[0]);
		Random r = new Random();
		int index = r.nextInt(keys.length);
		
		return providersMap.get(keys[index]).get();
	}

}
