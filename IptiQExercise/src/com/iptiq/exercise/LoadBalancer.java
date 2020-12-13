package com.iptiq.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class LoadBalancer {
	
	private HashMap<String, Provider> providersMap = new LinkedHashMap<String, Provider>();
	private HashMap<String, Provider> excludedProvidersMap = new HashMap<String, Provider>();
	private List<String> okChecks = new ArrayList<String>();
	private HashMap<String, Integer> requests = new HashMap<String, Integer>();
	private SelectionStrategy strategy;
	private int availableRequests;
	
	public LoadBalancer(SelectionStrategy strategy, int availableRequests) {
		this.strategy = strategy;
		this.availableRequests = availableRequests;
	}
	
	public boolean registerProvider(Provider provider) {
		//the total number of accepted providers is 10
		if ((providersMap.size() + excludedProvidersMap.size()) < 10) {
			providersMap.put(provider.get(), provider);
			requests.put(provider.get(), availableRequests);
			return true;
		} else {
			//Return false if the max number of
			// providers is reached
			return false;
		}
	}
	
	public String get() throws CapacityLimitException {
		List<String> ids = new ArrayList<String>();
		for (String id : providersMap.keySet()) {
			ids.add(new String(id));
		}
		String providerId = strategy.get(providersMap).get();
		ids.remove(providerId);
		while (ids.size() > 0 && requests.get(providerId) <= 0) {
			providerId = strategy.get(providersMap).get();
			ids.remove(providerId);
		}
		int value = requests.get(providerId);
		
		if (value > 0) {
			value--;
			requests.put(providerId, value);
			return providerId;
		} else {
			throw new CapacityLimitException("Requests limit exceeded");
		}
		
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
		requests.put(provider.get(), availableRequests);
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
