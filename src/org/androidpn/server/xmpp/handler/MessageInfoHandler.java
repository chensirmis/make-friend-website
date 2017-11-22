package org.androidpn.server.xmpp.handler;

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

import org.androidpn.server.model.User;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.service.UserExistsException;
import org.androidpn.server.service.UserNotFoundException;
import org.androidpn.server.service.UserService;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.androidpn.server.xmpp.session.ClientSession;
import org.androidpn.server.xmpp.session.Session;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.Message;
import org.xmpp.packet.PacketError;

public class MessageInfoHandler extends  MessageHandler{
	  private static final String NAMESPACE = "jabber:message:information";


	    private Element probeResponse;

	    /**
	     * Constructor.
	     */
	    public MessageInfoHandler() {
			/*
				        获取节点对象，并添加节点元素
				        
			*/
	        probeResponse = DocumentHelper.createElement(QName.get("body",NAMESPACE));//
	        probeResponse.addElement("information");
	        
	    }

	    
	    
		@Override
		public String getNamespace() {
			// TODO Auto-generated method stub
			return NAMESPACE;
		}

		@Override
		public Message handleMessage(Message packet)
				throws UnauthorizedException {
			System.out.println("-------------------------MessageInfoHandler");
			// TODO Auto-generated method stub
			Element body=packet.getElement();
			String message=body.elementText("body");
			if(Message.Type.normal.equals(packet.getType())){
				System.out.println("已经接收到");
			}
			System.out.println(message);
			
			return null;
		} 

}
