package fr.handco.lib.udp.client;

import java.nio.charset.StandardCharsets;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test();
	}

	public Test() {

		String data = "ASCI\n";
		byte[] sendData = data.getBytes(StandardCharsets.UTF_8);

		for (int i = 0; i < sendData.length; i++) {
			System.out.println("[" + i + "]" + " " + sendData[i]);
		}

		String decode = new String(sendData);

		System.out.println("decode=" + decode);

	}

}
