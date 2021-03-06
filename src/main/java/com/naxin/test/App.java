package com.naxin.test;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerInfo;

import weblogic.wsee.security.unt.ClientUNTCredentialProvider;

import com.comcast.billing.payment.client.PaymentServicePort;
import com.comcast.billing.payment.client.PaymentService_Impl;
import com.comcast.xml.payment.types.SubmitPaymentRequest;
import com.comcast.xml.types.ExceptionType;

public class App {

	public static void main(String[] args) throws RemoteException, ExceptionType {
		try {
			PaymentService_Impl jaxRpcService = new PaymentService_Impl();	
			Stub stub = (Stub) jaxRpcService.getPaymentServicePort();
			stub._setProperty("weblogic.wsee.transport.connection.timeout", 1000);
			stub._setProperty("weblogic.wsee.transport.read.timeout", 15000);
			
			stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, "https://esp-int.cable.comcast.com/PaymentService/16.13");
			
			String username="yourusername";
			String password = "yourpassword";
//			stub._setProperty(Stub.USERNAME_PROPERTY, "yourUserName");
//    		stub._setProperty(Stub.PASSWORD_PROPERTY, "yourPassword");
       		List<ClientUNTCredentialProvider> providers = new ArrayList<ClientUNTCredentialProvider>();
    		providers.add(new ClientUNTCredentialProvider(username.getBytes(), password.getBytes()));
    		stub._setProperty("weblogic.wsee.security.wss.CredentialProviderList", providers);        		    		
    		PaymentServicePort port = (PaymentServicePort) stub;
    		
    		//Register handler
            HashMap<String, Object> hMap = new HashMap<String, Object>();
   			hMap.put("username", username);
			hMap.put("password", password);
			hMap.put("wsse",Boolean.valueOf(true));
			hMap.put("addNonce", Boolean.valueOf(false));
			hMap.put("usePasswordDigest", Boolean.valueOf(false));
			hMap.put("addTimestamp", Boolean.valueOf(false));
			hMap.put("endpoint", "https://esp-int.cable.comcast.com/PaymentService/16.13");
			hMap.put("requestHeaderNamespace", "http://xml.comcast.com/types");
			
	        QName pn = new QName("http://xml.comcast.com/payment/services", "PaymentServicePort");

    		jaxRpcService.getHandlerRegistry().getHandlerChain(pn).add(
    				new HandlerInfo(SoapLoggingHandler.class, hMap, null));
    		
    		SubmitPaymentRequest request = new SubmitPaymentRequest();
    		request.setAmount(new BigDecimal("1000"));
    		port.submitPayment(request);
		} catch (ServiceException se) {
			se.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		} catch (ExceptionType et) {
			et.printStackTrace();
		}
 

	}

}
