package fr.handco.lib.meter;

import fr.handco.lib.time.Time;

/**
 * This class describes a basic HCHP meter frame
 * 
 * @author stephane.renouard@handco.fr
 * 
 */
public class MeterFrame {
	
	

	public String ADCO = "error";
	public String OPTARIF = "error";
	public int ISOUSC = -1;
	public int HCHC = -1;
	public int HCHP = -1;
	public int BASE = -1;
	public String PTEC = "error";
	public int IINST = -1;
	public int IMAX = -1;
	public int PAPP = -1;
	public String HHPHC = "error";
	public boolean MOTDETAT = false;

	public String TAG = "";

	/**
	 * Default Constructor
	 */
	public MeterFrame() {

		this.TAG = Time.timeStamp("").trim();

	}

	/**
	 * return meter frame
	 * 
	 * @return String
	 */
	public String getMeterFrame() {

		return ("ADCO=" + ADCO + " OPTARIF=" + OPTARIF + " ISOUSC=" + ISOUSC
				+ " HCHC=" + HCHC + " HCHP=" + HCHP + " BASE=" + BASE
				+ " PTEC=" + PTEC + " IINST=" + IINST + " IMAX=" + IMAX
				+ " PAPP=" + PAPP + " HHPHC=" + HHPHC + " TAG=" + TAG);
		
		
	}

	/**
	 * return frame length
	 * 
	 * @return int
	 */
	public int getMeterFrameSize() {

		return (getMeterFrame().length());

	}
	
	
	/**
	 * check is frame contains error
	 * 
	 * @return boolean
	 */
	public boolean isFrameContainsErrors(){
		
		boolean test = false;
		
		if(this.ADCO.matches("error"))
			test = true;
		
		if(this.OPTARIF.matches("error"))
			test = true;
		
		if(this.PTEC.matches("error"))
			test = true;
			
		return test;
	}

	
	/**
	 * check if frame is complete or not
	 * 
	 * @return boolean
	 */
	public boolean isFrameComplete() {

		boolean test = true;

		if (this.ADCO.matches(""))
			test = false;
		if (this.OPTARIF.matches(""))
			test = false;
		if (this.ISOUSC == -1)
			test = false;
		if (this.HCHC == -1)
			test = false;
		if (this.HCHP == -1)
			test = false;
		if (this.BASE == -1)
			test = false;
		if (this.PTEC.matches(""))
			test = false;
		if (this.IINST == -1)
			test = false;
		if (this.IMAX == -1)
			test = false;
		if (this.PAPP == -1)
			test = false;
		if (this.HHPHC.matches(""))
			test = false;
		if (this.MOTDETAT == false)
			test = false;
		if (this.TAG.matches(""))
			test = false;

		return test;

	}

	/*
	 * Make Frame with a data String
	 */
	public MeterFrame makeFrame(String string) {

		String[] tab = string.split(" ");

		for (int i = 0; i < tab.length; i++) {

			String tab2[] = (tab[i].split("="));

			if (tab2[0].matches("ADCO")) {
				this.ADCO = tab2[1];
			} else if (tab2[0].matches("OPTARIF")) {
				this.OPTARIF = tab2[1];
			} else if (tab2[0].matches("ISOUSC")) {
				this.ISOUSC = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("HCHP")) {
				this.HCHP = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("HCHC")) {
				this.HCHC = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("BASE")) {
				this.BASE = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("PTEC")) {
				this.PTEC = tab2[1];
			} else if (tab2[0].matches("IMAX")) {
				this.IMAX = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("IINST")) {
				this.IINST = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("PAPP")) {
				this.PAPP = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("HCHC")) {
				this.HCHC = Integer.parseInt(tab2[1]);
			} else if (tab2[0].matches("HHPHC")) {
				this.HHPHC = tab2[1];
			} else if (tab2[0].matches("TAG")) {
				this.TAG = tab2[1];
			}

		}

		this.MOTDETAT = true;

		return this;

	}

}
