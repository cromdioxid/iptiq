package com.iptiq.exercise;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class LoadBalancerTest {
	
	private LoadBalancer loadBalancer;
	private Provider p1;
	private Provider p2;
	
	@Before
	public void setUp() {
		loadBalancer = new LoadBalancer();
		p1 = new Provider();
		loadBalancer.registerProvider(p1);
		p2 = new Provider();
		loadBalancer.registerProvider(p2);
	}

	@Test
	public void testRegisterProvider() {
		for (int i = 2; i < 10; i++) {
			Provider provider = new Provider();
			assertTrue(loadBalancer.registerProvider(provider));
		}
		Provider provider = new Provider();
		assertFalse(loadBalancer.registerProvider(provider));
	}
	
	@Test
	public void getRandomProvider() {
		Set<String> registeredProviders = new HashSet<String>();
		registeredProviders.add(p1.get());
		registeredProviders.add(p2.get());
		String provider = loadBalancer.get();
		assertTrue(registeredProviders.contains(provider));
	}

}
