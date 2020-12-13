package com.iptiq.exercise;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class LoadBalancerTest {
	
	private LoadBalancer loadBalancer;
	
	@Before
	public void setUp() {
		SelectionStrategy randomStrategy = new RandomSelectionStrategy();
		loadBalancer = new LoadBalancer(randomStrategy,3);
	}

	@Test
	public void testRegisterProvider() {
		for (int i = 0; i < 10; i++) {
			Provider provider = new Provider();
			assertTrue(loadBalancer.registerProvider(provider));
		}
		Provider provider = new Provider();
		assertFalse(loadBalancer.registerProvider(provider));
	}
	
	@Test
	public void getRandomProvider() throws CapacityLimitException {
		Set<String> registeredProviders = new HashSet<String>();
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		registeredProviders.add(p1.get());
		Provider p2 = new Provider();
		registeredProviders.add(p2.get());
		registeredProviders.add(p2.get());
		String provider = loadBalancer.get();
		assertTrue(registeredProviders.contains(provider));
	}
	
	@Test
	public void getRoundRobinProviderTest() throws CapacityLimitException {
		RoundRobinSelectionStrategy rrStrategy = new RoundRobinSelectionStrategy();
		loadBalancer.setSelectionStrategy(rrStrategy);
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		Provider p2 = new Provider();
		loadBalancer.registerProvider(p2);
		assertEquals(p1.get(), loadBalancer.get());
		assertEquals(p2.get(), loadBalancer.get());
		Provider p3 = new Provider();
		loadBalancer.registerProvider(p3);
		assertEquals(p1.get(), loadBalancer.get());
		assertEquals(p2.get(), loadBalancer.get());
		assertEquals(p3.get(), loadBalancer.get());
		assertEquals(p1.get(), loadBalancer.get());
	}
	
	@Test
	public void excludeProviderTest() throws CapacityLimitException {
		RoundRobinSelectionStrategy rrStrategy = new RoundRobinSelectionStrategy();
		loadBalancer.setSelectionStrategy(rrStrategy);
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		Provider p2 = new Provider();
		loadBalancer.registerProvider(p2);
		assertTrue(loadBalancer.excludeProvider(p1.get()));
		assertFalse(loadBalancer.excludeProvider(p1.get()));
		assertNotEquals(p1.get(), loadBalancer.get());
		assertNotEquals(p1.get(), loadBalancer.get());
		assertTrue(loadBalancer.includeProvider(p1.get()));
		assertEquals(p2.get(), loadBalancer.get());
		assertEquals(p1.get(), loadBalancer.get());
		assertFalse(loadBalancer.includeProvider(p1.get()));
	}
	
	@Test
	public void checkProviders() throws CapacityLimitException {
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		Provider p2 = new Provider();
		p2.setIsAlive(false);
		loadBalancer.registerProvider(p2);
		loadBalancer.checkProviders();
		assertEquals(p1.get(), loadBalancer.get());
		assertEquals(p1.get(), loadBalancer.get());
	}
	
	@Test
	public void checkProviderAlive() throws CapacityLimitException {
		RoundRobinSelectionStrategy rrStrategy = new RoundRobinSelectionStrategy();
		loadBalancer.setSelectionStrategy(rrStrategy);
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		Provider p2 = new Provider();
		p2.setIsAlive(false);
		loadBalancer.registerProvider(p2);
		loadBalancer.checkProviders();
		assertEquals(p1.get(), loadBalancer.get());
		assertEquals(p1.get(), loadBalancer.get());
		p2.setIsAlive(true);
		loadBalancer.checkProviders();
		loadBalancer.checkProviders();
		assertEquals(p1.get(), loadBalancer.get());
		assertEquals(p2.get(), loadBalancer.get());
	}
	
	@Test(expected = CapacityLimitException.class)
	public void maxNumberOfRequests() throws CapacityLimitException {
		Provider p1 = new Provider();
		loadBalancer.registerProvider(p1);
		Provider p2 = new Provider();
		loadBalancer.registerProvider(p2);
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
		loadBalancer.get();
	}

}
