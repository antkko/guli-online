package com.macro;

/**
 * @auther macro
 * @description
 * @date 2023/12/12 11:59
 */
public class Execute implements  Runnable{

	public static volatile  int COUNT = 0;
	@Override
	public void run()
	{
		for(int i = 0; i < 10000; i++) {
			COUNT++;
		}
	}

	public static void main(final String[] args) throws InterruptedException
	{
		final Execute execute = new Execute();
		final Thread run1 = new Thread(execute);
		final Thread run2 = new Thread(execute);
		run1.start();
		run2.start();

		run1.join();
		run2.join();
		System.out.println(COUNT);

	}
}
