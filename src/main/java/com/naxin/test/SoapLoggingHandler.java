package com.naxin.test;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPException;

public class SoapLoggingHandler extends GenericHandler {

	@Override
	public QName[] getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public
	boolean handleRequest(MessageContext context) {
		SOAPMessageContext c = (SOAPMessageContext) context;
		try {
			c.getMessage().getSOAPBody().toString();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean handleResponse(MessageContext context) {
		return true;
		
	}

	public void init(HandlerInfo hi) {
		
	}


}
