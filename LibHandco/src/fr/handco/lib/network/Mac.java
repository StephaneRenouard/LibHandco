package fr.handco.lib.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Mac {

	String mac;

	public String getMacAddress(String IP) throws UnknownHostException,
			SocketException {

		InetAddress add = InetAddress.getByName(IP);

		NetworkInterface ni1 = NetworkInterface.getByInetAddress(add);

		byte[] mac1 = ni1.getHardwareAddress();

		System.out.println("taille mac=" + mac1.length);

		for (int k = 0; k < mac1.length; k++) {
			mac += mac1[k];

		}

		System.out.println("mac=" + mac);

		return mac;

	}

}
