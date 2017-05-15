package fr.handco.lib.flashbridge;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;

import fr.handco.lib.tools.Debug;

public class Listener {

	ServerSocket server = null;
	Vector<ClientManager> clients = new Vector<ClientManager>();

	private Debug debug;

	private FlashBridge flashbridge;

	/**
	 * default constructor
	 * 
	 * @param flashBridge
	 */
	public Listener(FlashBridge _flashBridge) {

		// initialize debug
		debug = new Debug(this);
		debug.state = true;

		// get flashbridge local
		flashbridge = _flashBridge;

	}

	/**
	 * create server socketListener
	 */
	public void socketListener() {

		try {
			server = new ServerSocket(1234);

			debug.print("Server socket initialized on port " + "1234");

		} catch (IOException e) {
			debug.print(e.getStackTrace().toString());
			// System.exit(-1);
		}
		do {
			try {
				do {

					debug.print("Waiting for clients to connect");

					ClientManager n = new ClientManager(server.accept(), this);

					clients.add(n);

					Thread t = new Thread(n);
					t.start();

				} while (true);
			} catch (IOException e) {

				debug.print("Read failed");

			}
			// System.exit(-1);
		} while (true);
	}

	/**
	 * send a message to all connected clients
	 * 
	 * @param msg
	 */
	public void sendAll(String msg) {

		debug.print("nombre de clients a joindre =" + clients.size());
		debug.print("message a envoyer =" + msg);

		for (int i = 0; i < clients.size(); i++) {

			ClientManager cm = (ClientManager) clients.get(i);
			cm.send(msg);
		}

	}

	/**
	 * close server
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		server.close();
	}

	/**
	 * handle incomming flash messages
	 * 
	 * @param flashMsg
	 */
	public void receiveMsgFromFlash(FlashMsg flashMsg) {
		flashbridge.fireMsgFromFlash(flashMsg);
	}

}