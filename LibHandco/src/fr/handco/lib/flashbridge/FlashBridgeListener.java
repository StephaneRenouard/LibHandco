package fr.handco.lib.flashbridge;

import java.util.EventListener;

/**
 * This interface must be implemented to send/receive messages from flashbridge
 * 
 * @author stephane.renouard@handco.fr
 * 
 */
public interface FlashBridgeListener extends EventListener {

	public void msgFromFlash(FlashMsg msg);

}
