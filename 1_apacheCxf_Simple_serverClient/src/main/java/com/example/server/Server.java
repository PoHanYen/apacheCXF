package com.example.server;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server{
	protected Server() throws Exception{
		HelloWorldImpl helloworldImpl=new HelloWorldImpl();
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
		svrFactory.setServiceClass(HelloWorld.class);
		svrFactory.setAddress("http://localhost:9000/Hello");
		svrFactory.setServiceBean(helloworldImpl);
		svrFactory.getFeatures().add(new LoggingFeature());
		svrFactory.create();
	}
	public static void main(String[] args) throws Exception{
		new Server();
		System.out.println("Server ready...");
		Thread.sleep(5*60*1000);
		System.out.println("Server exiting");
		System.exit(0);
	}
}
