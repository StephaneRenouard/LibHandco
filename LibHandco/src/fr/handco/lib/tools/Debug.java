package fr.handco.lib.tools;

/*
 * Debug.java
 *
 * Copyright (c) 2012 handco research, Inc. All Rights Reserved.
 *
 * Handco grants you ("Licensee") a non-exclusive, royalty free, license 
 * to use, modify and redistribute this software in source and binary
 * code form, provided that i) this copyright notice and license appear
 * on all copies of the software; and ii) Licensee does not utilize the
 * software in a manner which is disparaging to handco.
 *
 * This software is provided "AS IS," without a warranty of any kind.
 * 
 * ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES,
 * INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. 
 * HANDCO SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THE
 * SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL HANDCO BE LIABLE FOR 
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING
 * OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control
 * of aircraft, air traffic, aircraft navigation or aircraft
 * communications; or in the design, construction, operation or
 * maintenance of any nuclear facility. Licensee represents and
 * warrants that it will not use or redistribute the Software for such
 * purposes.
 * 
 */

import java.util.Calendar;

/**
 * This class is an alternative of the System.out.println() for debugging
 * 
 * @author stephane.renouard@handco.fr
 * 
 */
public class Debug {

	public boolean state = true;
	public boolean printClass = true;
	public boolean printTag = true;

	private boolean DEBUG_OVERRIDE = true;
	private Object object;

	/**
	 * Default constructor
	 * 
	 * @param Object
	 */
	public Debug(Object Class) {

		object = Class;

	}

	/**
	 * print tag, class and data
	 * 
	 * @param data
	 */
	public void print(String data) {

		try {
			if (state && DEBUG_OVERRIDE) {
				if (printClass) {
					data = "[" + object.getClass().getName().toString() + "] "
							+ data;
				}
				if (printTag) {

					data = Calendar.getInstance().getTime().toString() + " "
							+ data;
				}
				System.out.println(data);
			}
		} catch (Exception e) {
		} // do nothing

	}

	/**
	 * getter on printClass state
	 * 
	 * @return boolean
	 */
	public boolean getPrintClassState() {
		return printClass;
	}

	/**
	 * setter on printClass state
	 * 
	 * @param boolean state
	 */
	public void setPrintClassState(boolean state) {
		printClass = state;

	}

	/**
	 * getter on printTag state
	 * 
	 * @return boolean
	 */
	public boolean getPrintTagState() {
		return printTag;
	}

	/**
	 * setter on printTag state
	 * 
	 * @param boolean state
	 */
	public void setPrintTagState(boolean state) {
		printTag = state;

	}

}
