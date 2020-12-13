package com.iptiq.exercise;

import java.util.TimerTask;

public class HeartBeatChecker extends TimerTask {
	private LoadBalancer loadBalancer;
	
	public HeartBeatChecker(LoadBalancer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	@Override
	public void run() {
		loadBalancer.checkProviders();
	}

}
