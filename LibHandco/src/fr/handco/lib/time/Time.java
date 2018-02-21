package fr.handco.lib.time;

import java.util.Date;

/**
 * This class gives time tools
 * 
 * @author stephane.renouard@handco.fr
 * 
 */
public class Time {

	/**
	 * Give a time stamp in format 2011.11.29-10:15:23
	 * 
	 * @return String timeStamp
	 */
	@SuppressWarnings("deprecation")
	public static String timeStamp(String data) {

		Date dt = new Date();
		String str = "";
		String sMonth, sDay, sHour, sMin, sSec;

		sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));
		sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate()) : String
				.valueOf(dt.getDate()));
		str += String.valueOf(dt.getYear() + 1900) + "." + sMonth + "." + sDay
				+ "-";
		sHour = (dt.getHours() < 10 ? "0" + String.valueOf(dt.getHours())
				: String.valueOf(dt.getHours()));
		sMin = (dt.getMinutes() < 10 ? "0" + String.valueOf(dt.getMinutes())
				: String.valueOf(dt.getMinutes()));
		sSec = (dt.getSeconds() < 10 ? "0" + String.valueOf(dt.getSeconds())
				: String.valueOf(dt.getSeconds()));

		str += sHour + ":" + sMin + ":" + sSec + " " + data;
		
		
		return str;
		
	}
	
	/**
	 * Give a time stamp in format 2011.11.29-10:15:23 without space at the end
	 * 
	 * @return String timeStamp
	 */
	@SuppressWarnings("deprecation")
	public static String timeStampWithoutSpace(String data) {

		Date dt = new Date();
		String str = "";
		String sMonth, sDay, sHour, sMin, sSec;

		sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));
		sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate()) : String
				.valueOf(dt.getDate()));
		str += String.valueOf(dt.getYear() + 1900) + "." + sMonth + "." + sDay
				+ "-";
		sHour = (dt.getHours() < 10 ? "0" + String.valueOf(dt.getHours())
				: String.valueOf(dt.getHours()));
		sMin = (dt.getMinutes() < 10 ? "0" + String.valueOf(dt.getMinutes())
				: String.valueOf(dt.getMinutes()));
		sSec = (dt.getSeconds() < 10 ? "0" + String.valueOf(dt.getSeconds())
				: String.valueOf(dt.getSeconds()));

		str += sHour + ":" + sMin + ":" + sSec + "" + data;
		
		
		return str;
		
	}
	
	public static String timeStampMillis(String data){
		
		long millis = System.currentTimeMillis() % 1000;
		
		Date dt = new Date();
		String str = "";
		String sMonth, sDay, sHour, sMin, sSec;

		sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));
		sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate()) : String
				.valueOf(dt.getDate()));
		str += String.valueOf(dt.getYear() + 1900) + "." + sMonth + "." + sDay
				+ "-";
		sHour = (dt.getHours() < 10 ? "0" + String.valueOf(dt.getHours())
				: String.valueOf(dt.getHours()));
		sMin = (dt.getMinutes() < 10 ? "0" + String.valueOf(dt.getMinutes())
				: String.valueOf(dt.getMinutes()));
		sSec = (dt.getSeconds() < 10 ? "0" + String.valueOf(dt.getSeconds())
				: String.valueOf(dt.getSeconds()));

		str += sHour + ":" + sMin + ":" + sSec + ":"+ millis +" " + data;
		
		
		return str;
		
	}

	/**
	 * return current year
	 * 
	 * @return int Year
	 */
	@SuppressWarnings("deprecation")
	public static int getYear() {
		Date dt = new Date();
		return (dt.getYear() + 1900);
	}

	/**
	 * return current month
	 * 
	 * @return int Month
	 */
	@SuppressWarnings("deprecation")
	public static int getMonth() {
		Date dt = new Date();

		String sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));

		return Integer.parseInt(sMonth);

	}

	/**
	 * return current day
	 * 
	 * @return int Day
	 */
	@SuppressWarnings("deprecation")
	public static int getDay() {
		Date dt = new Date();

		String sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate())
				: String.valueOf(dt.getDate()));

		return Integer.parseInt(sDay);

	}

	/**
	 * Timestamping : return HMS Time
	 * 
	 * @return String time
	 */
	@SuppressWarnings("deprecation")
	public static String getHMSTime() {
		Date date = new Date();
		String time = date.getHours() + ":" + date.getMinutes() + ":"
				+ date.getSeconds();
		return (time);
	}

	/**
	 * return current hour
	 * 
	 * @return int Hour
	 */
	@SuppressWarnings("deprecation")
	public static int getHour() {
		Date dt = new Date();
		String sHour = (dt.getHours() < 10 ? "0"
				+ String.valueOf(dt.getHours()) : String.valueOf(dt.getHours()));
		return Integer.parseInt(sHour);
	}

	/**
	 * return current min
	 * 
	 * @return int Min
	 */
	@SuppressWarnings("deprecation")
	public static int getMin() {
		Date dt = new Date();
		String sMin = (dt.getMinutes() < 10 ? "0"
				+ String.valueOf(dt.getMinutes()) : String.valueOf(dt
				.getMinutes()));
		return Integer.parseInt(sMin.toString());
	}

	/**
	 * return current sec
	 * 
	 * @return int Sec
	 */
	@SuppressWarnings("deprecation")
	public static int getSec() {
		Date dt = new Date();
		String sSec = (dt.getSeconds() < 10 ? "0"
				+ String.valueOf(dt.getSeconds()) : String.valueOf(dt
				.getSeconds()));
		return Integer.parseInt(sSec);
	}

	/**
	 * Get Time
	 * 
	 * @return int
	 */
	public static int getTime() {
		Date date = new Date();
		return (int) date.getTime();
	}

	/**
	 * return date in format DMY
	 * 
	 * @return String DMY
	 */
	@SuppressWarnings("deprecation")
	public static String getDMY() {
		Date dt = new Date();
		String str = "";
		String sMonth, sDay;

		sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));
		sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate()) : String
				.valueOf(dt.getDate()));
		str += String.valueOf(dt.getYear() + 1900) + "." + sMonth + "." + sDay;

		return str;

	}
	
	// check if it is 00:00:00
	public static boolean checkForMidnight(){
		int time = getHour() + getMin() + getSec();
		
		if (time==0){
			return true;
		}
		else		
			return false;
		
	}
	
	/**
	 * return sec from currentTimeMillisec
	 * @param currentTimeMillisec
	 * @return long
	 */
	public static long get_number_sec_from_currentTimeMillis(long currentTimeMillisec){
		
		long time = System.currentTimeMillis();
		long elapsed_time = time - currentTimeMillisec;
		elapsed_time = elapsed_time/1000;
		
		return elapsed_time;
	}
	
	/**
	 * return hours from currentTimeMillisec
	 * @param currentTimeMillisec
	 * @return int
	 */
	public static int get_hour_from_currentTimeMillis(long currentTimeMillisec){
		
		long time = System.currentTimeMillis();
		long elapsed_time = time - currentTimeMillisec;
		elapsed_time = elapsed_time/1000;
		
		long min = elapsed_time/60;
		int hour = Math.round(min/60);
		
		return hour;
	}
	
	/**
	 * return mins from currentTimeMillisec
	 * @param currentTimeMillisec
	 * @return int
	 */
	public static int get_min_from_currentTimeMillis(long currentTimeMillisec){
		
		long time = System.currentTimeMillis();
		long elapsed_time = time - currentTimeMillisec;
		elapsed_time = elapsed_time/1000;
		
		long min = elapsed_time/60;
		int hour = Math.round(min/60);
		
		int min_int = Math.round(min-(hour*60)); 
		
		return min_int;
	}
	
	/**
	 * return mins from currentTimeMillisec
	 * @param currentTimeMillisec
	 * @return int
	 */
	public static int get_sec_from_currentTimeMillis(long currentTimeMillisec){
		
		long time = System.currentTimeMillis();
		long elapsed_time = time - currentTimeMillisec;
		
		elapsed_time = elapsed_time/1000;
		
		long min = elapsed_time/60;
		int hour = Math.round(min/60);
		
		int min_int = Math.round(min-(hour*60)); 
		
		int sec = Math.round(elapsed_time - (hour*60*60) - (min_int*60));
		
		return sec;
	}

} // end of Time class
