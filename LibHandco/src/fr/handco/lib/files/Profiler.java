package fr.handco.lib.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * This class allows to read/write data from a file
 * 
 * @author stephane.renouard@handco.fr
 * @date mai 2012
 * @version 1.0
 * 
 */
public class Profiler {

	/**
	 * Write data in file with key
	 * 
	 * @param path
	 *            (String)
	 * @param key
	 *            (String)
	 * @param text
	 *            (String)
	 * @throws IOException
	 */
	public static void write(String path, String key, String text)
			throws IOException {
		PrintWriter ecri;

		/*
		 * look for existing key
		 */
		BufferedReader lect;

		// count line number
		int nbr_line = 0;
		lect = new BufferedReader(new FileReader(path));
		while (lect.ready() == true) {
			lect.readLine();
			nbr_line += 1;
		}// end while loop

		// For debug purpose
		// System.out.println("nbr lignes=" + nbr_line);

		// create an array with nbr_line
		String[] tab = new String[nbr_line];

		// populate array
		int position = 0;
		lect = new BufferedReader(new FileReader(path));
		while (lect.ready() == true) {
			tab[position] = lect.readLine();
			// System.out.println("read " + tab[position] + " at position " +
			// position);
			position += 1;
		}// end while loop

		// we got data, now seek for key
		boolean key_found = false;
		for (int i = 0; i < tab.length; i++) {

			String data = tab[i];
			String[] split = data.split("=");

			if (split[0].matches(key)) {

				// System.out.println("Key found at position " + i);
				// System.out.println("replacing data " + split[1] + " with " +
				// text);

				tab[i] = split[0] + "=" + text;
				key_found = true;

			}

		}

		// write file
		ecri = new PrintWriter(new FileWriter(path, false)); // APPEND IS SET TO
																// FALSE
		// System.out.println("Writing " + tab.length + " lines");
		for (int i = 0; i < tab.length; i++) {

			// System.out.println("writing line [" + i + "] " + tab[i]);
			ecri.println(tab[i]);
		}
		ecri.flush();
		ecri.close();

		// add line if key is not found
		if (!key_found) { // key was not found

			// System.out.println("key not found, adding it");

			ecri = new PrintWriter(new FileWriter(path, true)); // APPEND IS SET
																// TO TRUE
			ecri.println(key + "=" + text);
			ecri.flush();
			ecri.close();

		} // end key not found

	} // end write method

	/**
	 * Write raw data in file
	 * 
	 * @param path
	 *            (String)
	 * @param text
	 *            (String)
	 * @throws IOException
	 */
	public static void writeRaw(String path, String text) throws IOException {
		PrintWriter ecri;
		ecri = new PrintWriter(new FileWriter(path));
		ecri.print(text);
		ecri.flush();
		ecri.close();
	}

	/**
	 * Read data in file with key
	 * 
	 * @param path
	 *            (String)
	 * @param key
	 *            (String)
	 * @return String
	 * @throws IOException
	 */
	public static String read(String path, String key) throws IOException {
		BufferedReader lect;
		String tmp = "";
		lect = new BufferedReader(new FileReader(path));
		boolean line_found = false;
		String value_to_return = "";
		while (lect.ready() == true) {
			tmp = lect.readLine();
			String data[] = tmp.split("=");
			if (data[0].matches(key)) {
				line_found = true;
				value_to_return = data[1];
			}

		}// end while loop

		if (!line_found) {
			// System.out.print("key not found");
			return "";
		} else {
			// System.out.print("key found with value " + value_to_return);
			return value_to_return;
		}

	}

	/**
	 * Read raw data in file
	 * 
	 * @param path
	 *            (String)
	 * @return String
	 * @throws IOException
	 */
	public static String readRaw(String path) throws IOException {
		BufferedReader lect;
		String tmp = "";
		lect = new BufferedReader(new FileReader(path));
		while (lect.ready() == true) {
			tmp += lect.readLine() + "\n";
		}// end while loop

		return tmp;
	}

}// end of class

