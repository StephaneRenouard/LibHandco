package fr.handco.lib.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import fr.edf.stabilo.lib.Tools;

public class Logger {

	private PrintWriter logger;

	private String path = "";
	private String logName = "";
	private String fileName = "NOEXIST";

	private boolean ready = false;

	private String currentLogDate;

	private boolean isRollingFile = false;

	private Object object;
	private boolean USE_CLASSNAME = false;

	/*
	 * log levels
	 */

	final public String DEBUG = "DEBUG";
	final public String INFO = "INFO";
	final public String WARNING = "WARNING";
	final public String ERROR = "ERROR";

	/**
	 * Default constructor (name path isRollingFile)
	 * 
	 * @param String
	 *            logName
	 * @param String
	 *            path
	 * @param boolean isRollingFile
	 */
	public Logger(String _logName, String _path, boolean _isRollingFile) {

		isRollingFile = _isRollingFile;
		logName = _logName;
		path = _path;
		USE_CLASSNAME = false;

		createLog(isRollingFile);

	}

	/**
	 * Charged constructor (name path isRollingFile class)
	 * 
	 * @param String
	 *            logName
	 * @param String
	 *            path
	 * @param boolean isRollingFile
	 */
	public Logger(String _logName, String _path, boolean _isRollingFile,
			Object _class) {

		isRollingFile = _isRollingFile;
		logName = _logName;
		path = _path;
		object = _class;
		USE_CLASSNAME = true;

		createLog(isRollingFile);

	}

	/**
	 * Create Log File
	 */
	private void createLog(boolean rolling) {
		try {
			if (rolling) {
				fileName = path + logprefix(logName);
			} else {
				fileName = path + logName;
			}
			// append = true by default
			logger = new PrintWriter(new BufferedWriter(new FileWriter(
					fileName, true)));
			ready = true;
		} catch (IOException e) {
			e.printStackTrace();
			ready = false;
		}
	}

	/**
	 * flat logging log transmitted data into fileName
	 * 
	 * @param String
	 *            data
	 */
	public void log(String data) {

		if (isRollingFile) {
			if (checkLogDate()) {
				logger.write(data + "\n");
				logger.flush();
			} else {
				logger.close();
				ready = false;
				createLog(isRollingFile);
				logger.write(data + "\n");
				logger.flush();
			}
		} else {
			logger.write(data + "\n");
			logger.flush();
		}
	}

	/**
	 * detailed logging
	 * 
	 * @param String
	 *            level
	 * @param String
	 *            data
	 * 
	 *            level is accessible in {Logger.DEBUG, Logger.INFO,
	 *            Logger.WARNING, Logger.error} or could be free
	 * 
	 */
	public void log(String level, String data) {

		String toWrite;

		if (USE_CLASSNAME) {
			toWrite = Tools.timeStamp("") + " " + level + " " + "["
					+ object.getClass().getName().toString() + "]" + " " + data;
		} else {
			toWrite = Tools.timeStamp("") + " " + level + " " + data;
		}

		if (isRollingFile) {
			if (checkLogDate()) {
				logger.write(toWrite + "\n");
				logger.flush();
			} else {
				logger.close();
				ready = false;
				createLog(isRollingFile);
				logger.write(toWrite + "\n");
				logger.flush();
			}
		} else {
			logger.write(toWrite + "\n");
			logger.flush();
		}

	}

	@SuppressWarnings("unused")
	private int getCurrentLogYear() {
		return (Integer.parseInt(currentLogDate.substring(0, 4)));
	}

	@SuppressWarnings("unused")
	private int getCurrentLogMonth() {
		return (Integer.parseInt(currentLogDate.substring(5, 7)));
	}

	@SuppressWarnings("unused")
	private int getCurrentLogDay() {
		return (Integer.parseInt(currentLogDate.substring(8, 10)));
	}

	@SuppressWarnings("deprecation")
	private boolean checkLogDate() {

		// for debug purpose
		// System.out.println("currentLogDate=" + currentLogDate);

		int Y = Integer.parseInt(currentLogDate.substring(0, 4));
		int M = Integer.parseInt(currentLogDate.substring(5, 7));
		int D = Integer.parseInt(currentLogDate.substring(8, 10));
		// System.out.println("Y=" + Y + " M=" + M + " D=" + D);

		Date date = new Date();

		int y = date.getYear() + 1900;
		int m = date.getMonth() + 1;
		int d = date.getDate();

		// System.out.println("y=" + y + " m=" + m + " d=" + d);

		if (Y == y && M == m && D == d) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Add yyyy-mm-dd- to logName
	 * 
	 * @param logName
	 * @return logFileName
	 */
	@SuppressWarnings("deprecation")
	private String logprefix(String logName) {

		Date dt = new Date();
		String str = "";
		String sMonth, sDay;

		sMonth = (dt.getMonth() + 1 < 10 ? "0"
				+ String.valueOf((dt.getMonth() + 1)) : String.valueOf(dt
				.getMonth() + 1));
		sDay = (dt.getDate() < 10 ? "0" + String.valueOf(dt.getDate()) : String
				.valueOf(dt.getDate()));
		str += String.valueOf(dt.getYear() + 1900) + "-" + sMonth + "-" + sDay;

		currentLogDate = str;

		str += "-";
		str += logName;

		return str;
	}

	/**
	 * getter for logName
	 * 
	 * @return logName
	 */
	public String getLogName() {
		return logName;
	}

	/**
	 * getter for file name
	 * 
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * getter to know log state
	 * 
	 * @return boolean
	 */
	public boolean isLogReady() {
		return ready;
	}

	/**
	 * close current logging file
	 */
	public void closeCurrentLog() {
		logger.flush();
		logger.close();
		ready = false;
	}

	/**
	 * flush log data
	 */
	public void flushCurrentLog() {
		logger.flush();
	}

	/**
	 * return if log is rolling file or not
	 * 
	 * @return boolean
	 */
	public boolean isRollingFileActive() {
		return isRollingFile;
	}

	/**
	 * return path
	 * 
	 * @return String
	 */
	public String getPath() {
		return path;
	}

}
