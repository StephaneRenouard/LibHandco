package fr.handco.lib.flashbridge;

import javax.swing.event.EventListenerList;
import fr.handco.lib.tools.Debug;

/**
 * 
 * @author stephane renouard stephane.renouard@handco.fr
 * @version 2.0
 * 
 *          This class provides a launcher for the FlashBridge
 */
public class FlashBridge implements Runnable {

	private long SLEEPING_TIME = 500; // in ms

	private Thread thread;

	final int PORT = 1234;

	Listener listener;

	private Debug debug;

	private final EventListenerList listeners = new EventListenerList();

	/**
	 * default constructor
	 * 
	 */
	public FlashBridge() {

		// launch debug
		debug = new Debug(this);
		debug.state = true;

		// hello world
		debug.print("Lauching FlashBridge...");

		// launch listener
		listener = new Listener(this);

		// starting thread
		thread = new Thread(this);
		thread.start();

	}

	/**
	 * Main entry point
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		new FlashBridge();

	}

	/*
	 * LISTENERS
	 */
	public void addFlashBridgeListener(FlashBridgeListener flashBridgeListener) {
		listeners.add(FlashBridgeListener.class, flashBridgeListener);
	}

	public void removeFlashBridgeListener(
			FlashBridgeListener flashBridgeListener) {
		listeners.remove(FlashBridgeListener.class, flashBridgeListener);

	}

	public FlashBridgeListener[] getFlashBridgeListener() {
		return listeners.getListeners(FlashBridgeListener.class);
	}

	protected void fireFlashBridgeListenerNewMsg(FlashMsg msg) {

		for (FlashBridgeListener flashBridgeListener : getFlashBridgeListener()) {

			// fire event
			flashBridgeListener.msgFromFlash(msg);

		}
	}

	public void run() {
		while (!Thread.interrupted())
			try {
				listener.socketListener();
				Thread.sleep(SLEEPING_TIME);
			} catch (InterruptedException e) {
				// ignore, treated by while loop
			}
	}

	/**
	 * getter on listener
	 * 
	 * @return
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * fire msg from flash
	 * 
	 * @param msg
	 * @return
	 */
	public void fireMsgFromFlash(FlashMsg msg) {

		debug.print("firing msgFromFlash event: " + msg.data + " from "
				+ msg.clientID);

		fireFlashBridgeListenerNewMsg(msg);

	}

}
