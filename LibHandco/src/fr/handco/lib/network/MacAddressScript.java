package fr.handco.lib.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MacAddressScript {
	
	
	public static String getMacAdress() throws IOException, InterruptedException{
		
		String fichier = "/data/mac.txt";
		
		File file = new File(fichier);
	    
		if (file.exists()) {
			// do nothing
		}
		else {
			// else generate it
			Runtime.getRuntime().exec("/data/getMacAddress.sh");	
		}
	   		
		Thread.sleep(1000);
		
		BufferedReader lect;
		String data = "";
		
		lect = new BufferedReader(new FileReader("/data/mac.txt"));
		
		while (lect.ready() == true) {
			data = lect.readLine();
		}// end while loop
		
		String tab[] = data.split(" ");
		
		return tab[tab.length-1];
		
	}

}
