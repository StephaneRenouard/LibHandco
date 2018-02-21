package fr.handco.lib.tests;

import fr.handco.lib.time.Time;

public class Test_Time {

	public static void main(String[] args) {
		new Test_Time();
	}
	
	public Test_Time() {
		
		System.out.println("starting");
		long start_time = System.currentTimeMillis();
	
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int sec = Time.get_sec_from_currentTimeMillis(start_time);
		int min = Time.get_min_from_currentTimeMillis(start_time);
		int hour = Time.get_hour_from_currentTimeMillis(start_time);
		
		System.out.println(hour + " hour " + min + " min " + sec + " sec");
		
		
	}

}
