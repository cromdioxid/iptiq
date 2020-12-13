package com.iptiq.exercise;

import java.util.Timer;

public class Main {
	private static long delay = 5000;
	private static int requestsPerProvider = 3;

	public static void main(String[] args) {
		RoundRobinSelectionStrategy strategy = new RoundRobinSelectionStrategy();
		LoadBalancer loadBalancer = new LoadBalancer(strategy, requestsPerProvider);
		Timer timer = new Timer();
		timer.schedule(new HeartBeatChecker(loadBalancer), delay);
	}

}
