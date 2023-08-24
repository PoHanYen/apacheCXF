package com.example.client;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.example.server.HelloWorld;

public class Client{
	private Client(){
	}
	public static void main(String[] args) throws Exception{
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getFeatures().add(new LoggingFeature());
        factory.setAddress("http://localhost:9000/helloWorld");
        HelloWorld client = factory.create(HelloWorld.class);
        System.out.println(client.sayHi("World"));
	}
}
