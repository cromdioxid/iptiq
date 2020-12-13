package com.iptiq.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoundRobinSelectionStrategy implements SelectionStrategy {
	
	private int currentProvider = 0;
	
	/*
	 * In case a new provider is added in the Round Robin selection strategy
	 * it is taken into account in the next iteration, because the currentProvider
	 * index is incremented before.**/

	@Override
	public Provider get(HashMap<String, Provider> providersList) {
		int nrOfProviders = providersList.size();
		List<String> keysList = new ArrayList<String>(providersList.keySet());
		Provider provider = providersList.get(keysList.get(currentProvider));
		currentProvider = (currentProvider + 1) % nrOfProviders;
		return provider;
	}
	
	

}
