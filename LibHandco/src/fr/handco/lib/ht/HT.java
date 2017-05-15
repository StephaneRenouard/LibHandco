package fr.handco.lib.ht;

import java.util.Hashtable;

public class HT {

	/**
	 * Extract an object from a HT
	 * 
	 * @param ht
	 * @param param
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static Object getParam(Hashtable ht, String param) {
		Object object = (Object) ht.get(param);
		return object;
	}

}
