package com.iptiq.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LoadBalancer {
	
	private HashMap<String, Provider> providersMap = new LinkedHashMap<String, Provider>();
	private HashMap<String, Provider> excludedProvidersMap = new HashMap<String, Provider>();
	private List<String> okChecks = new ArrayList<String>();
	private SelectionStrategy strategy;
	
	public LoadBalancer(SelectionStrategy strategy) {
		this.strategy = strategy;
	}
	
	public boolean registerProvider(Provider provider) {
		//the total number of accepted providers is 10
		if ((providersMap.size() + excludedProvidersMap.size()) < 10) {
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
	
	public boolean excludeProvider(String id) {
		Provider provider = providersMap.get(id);
		if (provider == null) {
			// the provider id is not existing in our
			//providers list
			return false;
		}
		providersMap.remove(id);
		excludedProvidersMap.put(id, provider);
		return true;
	}
	
	public boolean includeProvider(String id) {
		Provider provider = excludedProvidersMap.get(id);
		if (provider == null) {
			//the provider is not present in our excluded
			//providers list
			return false;
		}
		excludedProvidersMap.remove(id);
		providersMap.put(id, provider);
		return true;
	}
	
	public void checkProviders() {
		for (String key : providersMap.keySet()) {
			Provider provider = providersMap.get(key);
			if (!provider.check()) {
				excludeProvider(key);
			}
		}
		
		//check if excluded providers are alive
		for (String key : excludedProvidersMap.keySet()) {
			Provider provider = excludedProvidersMap.get(key);
			if (provider.check()) {
				if (okChecks.contains(provider.get())) {
					includeProvider(provider.get());
					okChecks.remove(provider.get());
				} else {
					okChecks.add(provider.get());
				}
			} else {
				okChecks.remove(provider.get());
			}
		}
	}

}
