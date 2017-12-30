package uk.co.ramyun.herblore.util;

public class Timer {

	private long period, start;

	public Timer() {
		this.period = 0L;
		start = System.currentTimeMillis();
	}

	public Timer(long period, long starting) {
		this.period = period;
		start = System.currentTimeMillis() - starting;
	}

	public Timer(long period) {
		this.period = period;
		start = System.currentTimeMillis();
	}

	public long getElapsed() {
		return System.currentTimeMillis() - start;
	}

	public boolean isRunning() {
		return getElapsed() <= period;
	}

	public void reset() {
		start = System.currentTimeMillis();
	}

	public void stop() {
		period = 0;
	}

	private String format() {
		long sec = getElapsed() / 1000, d = sec / 86400, h = sec / 3600 % 24, m = sec / 60 % 60, s = sec % 60;
		return (d < 10 ? "0" + d : d) + ":" + (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":"
				+ (s < 10 ? "0" + s : s);
	}

	@Override
	public String toString() {
		return format();
	}
}
