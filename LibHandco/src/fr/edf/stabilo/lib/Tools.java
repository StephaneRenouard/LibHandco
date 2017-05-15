package fr.edf.stabilo.lib;

import java.util.Date;

/**
 * Tools for meter date validation and timestamping
 * 
 * @author stephane renouard
 * 
 */
public class Tools {

	boolean DEBUG = false;

	/**
	 * This static method timestamp data each time a string is given as argument
	 * 
	 * @param data
	 *            (String)
	 * @return String
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
		str += String.valueOf(dt.getYear() + 1900) + "-" + sMonth + "-" + sDay
				+ " ";
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
	 * this static method allows data validation
	 * 
	 * @param data
	 *            (String)
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	public static boolean dataValid(String received_data) {

		boolean isValid = true;

		String ADCO;
		String OPTARIF;
		int ISOUSC;
		int HCHC;
		int HCHP;
		String PTEC;
		int IINST;
		int IMAX;
		int PAPP;
		String HHPHC;

		/*
		 * ADCO ="error"; OPTARIF = "error"; ISOUSC = -1; HCHC = -1; HCHP = -1;
		 * PTEC = "error"; IINST = -1; IMAX = -1; PAPP = -1; HHPHC = "error";
		 */

		// split frame
		String trame[] = received_data.split(" ");

		// check for bad parameters
		for (int i = 0; i < trame.length; i++) {

			if (trame[i].contains("error")) {
				isValid = false;
				System.out.println(Tools.timeStamp("[VALIDATOR] Frame "
						+ received_data + " contains invalid data"));
			}

			if (trame[i].contains("-1")) {
				isValid = false;
				System.out.println(Tools.timeStamp("[VALIDATOR] Frame "
						+ received_data + " contains invalid data"));
			}

		}

		return isValid;

	}

	/**
	 * Transform frame to be more flexible
	 */
	public static String frameTransform(String frame) {

		@SuppressWarnings("unused")
		boolean isValid = true;
		boolean DEBUG = false;

		String ADCO = "";
		String OPTARIF = "";
		int ISOUSC = -1;
		int HCHC = -1;
		int HCHP = -1;
		String PTEC = "";
		int IINST = -1;
		int IMAX = -1;
		int PAPP = -1;
		@SuppressWarnings("unused")
		String HHPHC = "0";

		/*
		 * ADCO ="error"; OPTARIF = "error"; ISOUSC = -1; HCHC = -1; HCHP = -1;
		 * PTEC = "error"; IINST = -1; IMAX = -1; PAPP = -1; HHPHC = "null";
		 */

		// split frame
		String trame[] = frame.split(" ");

		for (int i = 0; i < trame.length; i++) {

			String param[] = trame[i].split("=");

			if (param[0].contains("ADCO")) {
				ADCO = param[1];
			} else if (param[0].contains("OPTARIF")) {

				if (param[1].matches("TH..")) {
					OPTARIF = "BASE";
				} else {
					OPTARIF = param[1];
				}
			} else if (param[0].contains("ISOUSC")) {
				ISOUSC = Integer.parseInt(param[1]);
			} else if (param[0].contains("BASE") && OPTARIF.matches("BASE")) {
				HCHP = Integer.parseInt(param[1]);
			} else if (param[0].contains("HCHC")) {
				if (OPTARIF.matches("BASE")) {
					HCHC = 0;
				} else {
					HCHC = Integer.parseInt(param[1]);
				}
			} else if (param[0].contains("HCHP")) {
				if (OPTARIF.matches("BASE")) {
					// nothing to do
				} else {
					HCHP = Integer.parseInt(param[1]);
				}
			} else if (param[0].contains("PTEC")) {
				if (OPTARIF.matches("BASE")) {
					PTEC = "HP..";
				} else if (param[1].matches("TH..")) {
					PTEC = "HP..";
				} else {
					PTEC = param[1];
				}
			} else if (param[0].contains("IINST")) {
				IINST = Integer.parseInt(param[1]);
			} else if (param[0].contains("IMAX")) {
				IMAX = Integer.parseInt(param[1]);
			} else if (param[0].contains("PAPP")) {
				PAPP = Integer.parseInt(param[1]);
				if (PAPP == -1) {
					System.out.println("PAPP non transmise");
					PAPP = 0;
				}
			}
		}

		frame = "ADCO=" + ADCO + " OPTARIF=" + OPTARIF + " ISOUSC=" + ISOUSC
				+ " HCHC=" + HCHC + " HCHP=" + HCHP + " PTEC=" + PTEC
				+ " IINST=" + IINST + " IMAX=" + IMAX + " PAPP=" + PAPP
				+ " HHPHC=" + 0;

		if (DEBUG)
			System.out.println("CHECKEDframe=" + frame);

		// 2nd check
		trame = frame.split(" ");

		isValid = true;

		// check for bad parameters
		for (int i = 0; i < trame.length; i++) {

			if (trame[i].contains("error")) {
				isValid = false;
				// System.out.println(Tools.timeStamp("[VALIDATOR] Frame " +
				// frame
				// + " contains invalid data"));
				System.out.println("[VALIDATOR] Error sur le champs "
						+ trame[i]);
			}

			if (trame[i].contains("-1")) {
				isValid = false;
				// System.out.println(Tools.timeStamp("[VALIDATOR] Frame " +
				// frame
				// + " contains invalid data"));
				System.out.println("[VALIDATOR] Error sur le champs "
						+ trame[i]);
			}

		}

		return frame;

	}

}
