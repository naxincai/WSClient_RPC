package com.naxin.test;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

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
			
			stub._setProperty(Stub.USERNAME_PROPERTY, "yourUserName");
    		stub._setProperty(Stub.PASSWORD_PROPERTY, "yourPassword");
    		
    		PaymentServicePort port = (PaymentServicePort) stub;
    		
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
