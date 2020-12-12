package com.iptiq.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoundRobinSelectionStrategy implements SelectionStrategy {
	
	private int currentProvider = 0;

	@Override
	public Provider get(HashMap<String, Provider> providersList) {
		int nrOfProviders = providersList.size();
		List<String> keysList = new ArrayList<String>(providersList.keySet());
		Provider provider = providersList.get(keysList.get(currentProvider));
		currentProvider = (currentProvider + 1) % nrOfProviders;
		return provider;
	}
	
	

}
