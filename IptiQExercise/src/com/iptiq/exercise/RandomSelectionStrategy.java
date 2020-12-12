package com.iptiq.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomSelectionStrategy implements SelectionStrategy{

	@Override
	public Provider get(HashMap<String, Provider> providersList) {
		Random r = new Random();
		int index = r.nextInt(providersList.size());
		List<String> keysList = new ArrayList<String>(providersList.keySet());
		
		return providersList.get(keysList.get(index));
	}
	

}
