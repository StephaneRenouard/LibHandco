package fr.handco.lib.tests;

import fr.handco.lib.time.Time;

public class Test_Time {

	public static void main(String[] args) {
		new Test_Time();
	}
	
	public Test_Time() {
		
		long start_time = System.currentTimeMillis();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int result = Time.get_sec_from_currentTimeMillis(start_time);
		
		System.out.println(result);
	}

}
