package com.thinkerwolf.frameworkstudy.concurrent;

public class NonVisibility {

	static boolean ready = false;
	static int num = 0;

	static class ReadThread extends Thread {
		@Override
		public void run() {
			while (!ready) {
				Thread.yield();
			}
			System.err.println(num);
		}
	}

	public static void main(String[] args) {
		new ReadThread().start();
		num = 42;
		ready = true;
	}

}
