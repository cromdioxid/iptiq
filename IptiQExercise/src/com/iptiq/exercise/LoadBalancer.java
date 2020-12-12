package com.iptiq.exercise;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LoadBalancer {
	
	private HashMap<String, Provider> providersMap = new LinkedHashMap<String, Provider>();
	private SelectionStrategy strategy;
	
	public LoadBalancer(SelectionStrategy strategy) {
		this.strategy = strategy;
	}
	
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
		return strategy.get(providersMap).get();
	}
	
	public void setSelectionStrategy(SelectionStrategy strategy) {
		this.strategy = strategy;
	}

}
