package fr.handco.lib.udp.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import javax.swing.event.EventListenerList;

import fr.handco.lib.tools.Debug;

/**
 * handco UDP client
 * 
 * @author stephane.renouard@handco.fr
 * @date 2012, march
 * @version 1.0
 * 
 * 
 *          According to java doc, encoding charset could be: US-ASCII Seven-bit
 *          ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode
 *          character set ISO-8859-1 ISO Latin Alphabet No. 1, a.k.a.
 *          ISO-LATIN-1 UTF-8 Eight-bit UCS Transformation Format UTF-16BE
 *          Sixteen-bit UCS Transformation Format, big-endian byte order
 *          UTF-16LE Sixteen-bit UCS Transformation Format, little-endian byte
 *          order UTF-16 Sixteen-bit UCS Transformation Format, byte order
 *          identified by an optional byte-order mark
 * 
 * 
 */
public class UDPClient implements Runnable {

	/*
	 * IP and PORT to connect
	 */
	public static String IP;
	public static int PORT;

	/*
	 * UDP main streams
	 */
	private DatagramSocket clientSocket;
	private InetAddress IPAddress;
	private DatagramPacket receivePacket;
	private int SOCKET_TIMEOUT = 100; // in ms

	/*
	 * data handling
	 */
	String DataToSend = "";
	private byte[] sendData = new byte[512];
	private byte[] receiveData = new byte[512];

	/*
	 * Debug status
	 */
	private Debug debug;
	public final String VERSION = "1.0";
	public boolean DEBUG_STATE = true;

	/*
	 * Listeners
	 */
	private final EventListenerList listeners = new EventListenerList();

	/*
	 * status & flags
	 */
	boolean connection = false;

	/*
	 * Thread
	 */
	private long SLEEPING_TIME = 1000; // in ms
	private Thread thread;

	/**
	 * Default constructor
	 */
	public UDPClient(String t_IP, int t_PORT) {

		/*
		 * initialize debug
		 */
		debug = new Debug(this);
		debug.state = DEBUG_STATE;
		debug.print("Starting UDP client version " + VERSION);

		/*
		 * get variables locally
		 */
		IP = t_IP;
		PORT = t_PORT;

		// general UDP client thread
		thread = new Thread(this);
		thread.start();

		/*
		 * initialize UDP socket
		 */
		openSocket();

	}

	/**
	 * Send UDP data
	 * 
	 * @param data
	 * @return
	 */
	public boolean sendUDP(String data) {

		// sendData = data.getBytes();

		sendData = data.getBytes(StandardCharsets.UTF_8);

		DatagramPacket sendPacket = new DatagramPacket(sendData,
				sendData.length, IPAddress, PORT);

		try {
			debug.print("sending " + data + " size=" + sendData.length);
			clientSocket.send(sendPacket);

			/*
			 * initialize datagramm packet
			 */
			receivePacket = new DatagramPacket(receiveData, receiveData.length);

			clientSocket.receive(receivePacket);
			String modifiedSentence = new String(receivePacket.getData());
			debug.print("received on UDP socket=" + modifiedSentence);
			this.fireUDPListenerNewMsg(modifiedSentence);

			return true;
		} catch (IOException e) {
			debug.print("ERROR UDP IOException while sending message");
			debug.print(e.getMessage());
			// e.printStackTrace();
			return false;
		}

	}

	/**
	 * initialize UDP client and open communication socket
	 */
	public void openSocket() {

		/*
		 * try to open socket
		 */
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(IP);

		} catch (SocketException e) {
			connection = false;
			debug.print("connection is set to FALSE");
			debug.print("ERROR SocketeException");
			debug.print(e.getMessage());
			// e.printStackTrace();
		} catch (UnknownHostException e) {
			connection = false;
			debug.print("connection is set to FALSE");
			debug.print("ERROR UnknownHostException");
			debug.print(e.getMessage());
			// e.printStackTrace();
		}

		try {
			clientSocket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e) {
			debug.print("SoEx");
			// e.printStackTrace();
		}

		connection = true;
		debug.print("connection is set to TRUE");

		/*
		 * initialize data structure
		 */
		sendData = new byte[1024];
		receiveData = new byte[1024];

	}

	/**
	 * close client socket
	 */
	public void closeSocket() {
		debug.print("closing client socket");
		clientSocket.close();
		connection = false;
		debug.print("connection is set to FALSE");
	}

	/**
	 * close UDP client
	 */
	@SuppressWarnings("deprecation")
	public void closeUDPclient() {

		if (connection == true) {
			closeSocket();
		}
		thread.stop();
	}

	/**
	 * LISTENERS
	 */
	public void addUDPListener(UDPListener uDPListener) {
		listeners.add(UDPListener.class, uDPListener);
	}

	public void removeUDPListener(UDPListener uDPListener) {
		listeners.remove(UDPListener.class, uDPListener);

	}

	public UDPListener[] getUDPListener() {
		return listeners.getListeners(UDPListener.class);
	}

	protected void fireUDPListenerNewMsg(String msg) {

		for (UDPListener uDPListener : getUDPListener()) {

			debug.print("firing UDP rcv msg: " + msg);

			// fire event new UDP msg
			uDPListener.newUDPMsg(msg);

		}
	}

	/**
	 * main launcher for UDP client (test only)
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		if (args.length == 2) {
			// launch UDP client
			new UDPClient(args[1], Integer.getInteger(args[2]));
		} else {
			// test purpose
			new UDPClient("192.168.0.44", 21000);
		}

	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {

			try {
				Thread.sleep(SLEEPING_TIME);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				debug.print("Stopping TCP client thread");
			}
		}
	} // end run() method

}
