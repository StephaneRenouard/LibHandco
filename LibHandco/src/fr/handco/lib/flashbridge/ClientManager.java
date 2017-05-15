package fr.handco.lib.flashbridge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import fr.handco.lib.tools.Debug;

/**
 * This class refers as client manager for multiple connections
 */
class ClientManager implements Runnable {

	Socket socket;

	BufferedReader in;

	PrintWriter out;

	StringBuffer lineBuffer;

	int streamResult;

	char cbuf[];

	private Debug debug;

	private Listener listener;

	/**
	 * Constructor
	 * 
	 * @param listener
	 * 
	 * @param Socket
	 *            client
	 */
	public ClientManager(Socket client, Listener _listener) {

		// activate debug
		debug = new Debug(this);
		debug.state = true;

		// get socket local
		socket = client;

		debug.print("New client connection from "
				+ socket.getInetAddress().toString());

		// get listener local
		listener = _listener;

		// initialize in and out
		in = null;
		out = null;
		lineBuffer = new StringBuffer();
		cbuf = new char[1];

		try {

			debug.print("Initializing client streams");

			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			InputStreamReader isr = new InputStreamReader(is, "8859_1");

			in = new BufferedReader(isr);
			out = new PrintWriter(os, true);

		} catch (IOException e) {

			debug.print("IOException");
			debug.print(e.toString());

		}

		debug.print("ready to share data with client "
				+ client.getInetAddress());

	}

	/**
	 * main process
	 */
	public void run() {

		try {
			do {
				lineBuffer.delete(0, lineBuffer.length());
				do {
					streamResult = in.read(cbuf, 0, 1);
					lineBuffer.append(cbuf[0]);
				} while (cbuf[0] != '\0');

				lineBuffer.deleteCharAt(lineBuffer.length() - 1);

				debug.print("recu=" + lineBuffer.toString().trim());

				/*
				 * Policy File Request
				 */
				if (lineBuffer.toString().contains("<policy-file-request/>")) {

					debug.print("Policy File Requested");

					@SuppressWarnings("unused")
					String aenvoyer = "<?xml version=\"1.0\"?><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"100-20000\"/></cross-domain-policy>\0";

					// Listener.sendAll(aenvoyer.toString());

					debug.print("WARNING, Policy File Request not implemented");

				}

				/*
				 * Removing client when they left
				 */
				if (lineBuffer.toString().length() == 0) {

					debug.print("removing ClientManager instance");

					// listener.clients.remove(this);

					debug.print("WARNING removing code not implemented");
				} else {

					// get msg from flash here
					FlashMsg flashMsg = new FlashMsg();
					flashMsg.clientID = socket.getInetAddress()
							.getHostAddress().toString(); // we use IP adress
															// for clientID
					flashMsg.data = lineBuffer.toString().trim();
					listener.receiveMsgFromFlash(flashMsg);

				}
			} while (streamResult != -1);

		} catch (IOException e) {

			debug.print("IO Exception");
			debug.print(e.toString());

		}
	}

	/**
	 * send message
	 * 
	 * @param String
	 *            msg
	 */
	public void send(String msg) {

		debug.print("Utilisation de la method send dans le ClientManager");

		debug.print("Message à envoyer: " + msg);

		try {

			out.println("<text action=\"message\">" + msg + "</text>\0");
		} catch (Exception e) {
			debug.print(e.toString());
		}
	}

}