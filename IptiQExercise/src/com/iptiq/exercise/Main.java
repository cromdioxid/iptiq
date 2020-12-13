package com.iptiq.exercise;

import java.util.Timer;

public class Main {
	private static long delay = 5000;

	public static void main(String[] args) {
		RoundRobinSelectionStrategy strategy = new RoundRobinSelectionStrategy();
		LoadBalancer loadBalancer = new LoadBalancer(strategy);
		Timer timer = new Timer();
		timer.schedule(new HeartBeatChecker(loadBalancer), delay);
	}

}
