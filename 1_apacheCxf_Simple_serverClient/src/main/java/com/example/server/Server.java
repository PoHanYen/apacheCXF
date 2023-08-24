package com.example.server;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class Server{
	protected Server() throws Exception{
		HelloWorldImpl helloworldImpl=new HelloWorldImpl();
		System.out.println("Starting Server");
        HelloWorldImpl implementor = new HelloWorldImpl();
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setServiceClass(HelloWorld.class);
        svrFactory.setAddress("http://localhost:9000/helloWorld");
        svrFactory.setServiceBean(implementor);
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
