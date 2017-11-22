package org.androidpn.server.xmpp.handler;

import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.router.PacketDeliverer;
import org.androidpn.server.xmpp.session.SessionManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmpp.packet.Message;
import org.xmpp.packet.Packet;
import org.xmpp.packet.PacketError;

public abstract class MessageHandler {
	
	 protected final Log log = LogFactory.getLog(getClass());

	    protected SessionManager sessionManager;

	    /**
	     * 
	     * Constructor.
	     * 
	     */
	    public MessageHandler() {
	        sessionManager = SessionManager.getInstance();
	    }

	    /**
	     * Processes the received IQ packet.
	     * IQHandler由IQRegisterHandler.java继承
	     * @param packet the packet
	     */
	    public void process(Packet packet) {
	    	Message message=(Message)packet;
	    	try {
				handleMessage(message);
			} catch (UnauthorizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    /**
	     * Handles the received IQ packet.
	     * 
	     * @param packet the packet
	     * @return the response to send back
	     * @throws UnauthorizedException if the user is not authorized
	     */
	    public abstract Message handleMessage(Message packet) throws UnauthorizedException;

	    /**
	     * Returns the namespace of the handler.
	     * 
	     * @return the namespace
	     */
	    public abstract String getNamespace();
	
	
	

}
