package org.androidpn.server.xmpp.handler;

import org.androidpn.server.model.Information;
import org.androidpn.server.service.InformationService;
import org.androidpn.server.service.ServiceLocator;
import org.androidpn.server.xmpp.UnauthorizedException;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

public class IQDeleteHandler extends IQHandler{
	
	 private static final String NAMESPACE = "jabber:iq:comfirm";
	 private InformationService  informationService;
	 public IQDeleteHandler(){
		 informationService=ServiceLocator.getInformationService();
	 }

	 
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return NAMESPACE;
	}

	@Override
	public IQ handleIQ(IQ packet) throws UnauthorizedException {
		  Element iq = packet.getChildElement();
		  Element query=iq.element("query");
		  String username=query.elementText("username");
		  Information information=new Information();
		  information.setUsername(username);
		  informationService.delete(information);		  
		return null;
	}

}
