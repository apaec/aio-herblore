package uk.co.ramyun.herblore.util;

import java.util.Random;

public class GaussianRandom {
	/**
	 * @author © Michael 21 Apr 2017
	 * @file GaussianRandom.java
	 */

	private final Random random = new Random();

	public int gRandomAboveZero(int mean, int standardDeviation) {
		int generated = gRandom(mean, standardDeviation);
		return generated > 0 ? generated : mean;
	}

	public int gRandom(int mean, int standardDeviation) {
		return (int) (mean + random.nextGaussian() * standardDeviation);
	}

	public long gRandom(long mean, long standardDeviation) {
		return (long) (mean + random.nextGaussian() * standardDeviation);
	}

	public int gRandomInRange(int min, int max) {
		int mean = (min + max) / 2, std = (max - mean) / 3, n = gRandom(mean, std);
		while (n < min || n > max)
			n = gRandom(mean, std);
		return n;
	}

	public long gRandomInRange(long min, long max) {
		long mean = (min + max) / 2, std = (max - mean) / 3, n = gRandom(mean, std);
		while (n < min || n > max)
			n = gRandom(mean, std);
		return n;
	}

	public String gRandomInStringArray(String[] arr) {
		return arr[gRandomInRange(0, arr.length - 1)];
	}

}
