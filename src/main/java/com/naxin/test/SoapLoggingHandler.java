package com.naxin.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.namespace.QName;
import javax.xml.rpc.handler.GenericHandler;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.MessageContext;
import javax.xml.rpc.handler.soap.SOAPMessageContext;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;


public class SoapLoggingHandler extends GenericHandler {

	private HandlerInfo handlerInfo;
	
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
		
		addServiceContextToHeader(context, "requestHeaderNamespace");
		return true;
	}
	
	public boolean handleResponse(MessageContext context) {
		return true;
		
	}

	public void init(HandlerInfo hi) {
		this.handlerInfo = hi;
	}
	
	protected boolean addServiceContextToHeader(MessageContext context,
			String headerType) {
		SOAPMessageContext messageContext = (SOAPMessageContext) context;

		try {
			SOAPHeader header = messageContext.getMessage().getSOAPPart()
					.getEnvelope().getHeader();

			String headerNamespace = (String) this.handlerInfo
					.getHandlerConfig().get("requestHeaderNamespace");

			SOAPElement hdr = header.addChildElement(headerType,
					"typ", headerNamespace);
			addChildElement(hdr, "timestamp", getISO8601DateTime(new java.util.Date()));
			if ("requestHeader".equals(headerType)) {

				addChildElement(hdr, "sourceSystemId", "yourSystemID");
				addChildElement(hdr, "sourceSystemUserId", "yoursourceSystemUserId");
				addChildElement(hdr, "sourceServerId", "youSourceServerID");
			}
			addChildElement(hdr, "trackingId", "yourTrackingID");
		} catch (SOAPException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static String getISO8601DateTime(Date date) {
        SimpleDateFormat ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        TimeZone timeZone = TimeZone.getDefault(); //local JVM time zone
        ISO8601Local.setTimeZone(timeZone);
        DecimalFormat twoDigits = new DecimalFormat("00");

        int offset = ISO8601Local.getTimeZone().getOffset(date.getTime());
        String sign = "+";
        if (offset < 0) {
          offset = -offset;
          sign = "-";
        }
        int hours = offset / 3600000;
        int minutes = (offset - hours * 3600000) / 60000;

        String ISO8601Now = ISO8601Local.format(date) + sign +
          twoDigits.format(hours) + ":" + twoDigits.format(minutes);
        return ISO8601Now;
      }
	private void addChildElement(SOAPElement elem, String name, Object value)
			throws SOAPException {
		if (value != null) {
			SOAPElement node = elem.addChildElement(name,
					"typ");
			node.addTextNode(value.toString());
		}
	}

}
