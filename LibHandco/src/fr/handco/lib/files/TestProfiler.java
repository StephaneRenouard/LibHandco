package fr.handco.lib.files;

import java.io.IOException;

public class TestProfiler {

	final String PATH = "c:/scratch/test.txt";
	final String TEXT = "hello world";
	final String KEY = "key";

	final boolean TEST_RAW = false;
	final boolean TEST_WRITE = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestProfiler();

	}

	/**
	 * Default constructor
	 */
	public TestProfiler() {

		if (TEST_RAW) {
			/*
			 * WRITE RAW
			 */
			System.out.println("Writing raw data '" + TEXT + "' in file "
					+ PATH);

			try {

				Profiler.writeRaw(PATH, TEXT);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * READ RAW
			 */
			System.out.println("Reading raw content of file " + PATH);

			try {

				String data = Profiler.readRaw(PATH);
				System.out.println(data);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (TEST_WRITE) {
			/*
			 * WRITE KEY
			 */
			try {
				Profiler.write(PATH, KEY, TEXT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * READ KEY
		 */
		try {
			System.out.println(Profiler.read(PATH, KEY));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (TEST_RAW) {
			/*
			 * READ RAW
			 */
			System.out.println("Reading raw content of file " + PATH);

			try {

				String data = Profiler.readRaw(PATH);
				System.out.println(data);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
