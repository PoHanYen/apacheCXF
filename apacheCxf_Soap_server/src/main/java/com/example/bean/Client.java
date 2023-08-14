package com.example.bean;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.example.service.CxfDemoService;

public class Client{
	public static void main(String[] args){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(CxfDemoService.class);
		factory.setAddress("http://localhost:8080/apacheCxf_Soap_server/cxf/CxfDemoService");
		CxfDemoService client = (CxfDemoService) factory.create();
		String reply = client.hello("Rayjun");
		System.out.println("Server : " + reply); // Server: hello rayjun
	}
}
