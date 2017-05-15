package fr.handco.lib.udp.client;

import java.util.EventListener;

public interface UDPListener extends EventListener {

	void newUDPMsg(String msg);

}
