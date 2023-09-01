package com.example.client;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.example.service.CxfDemoService;
import com.example.testService.Person;

@SuppressWarnings("deprecation")
public class Client{
	public static void main(String[] args) throws Exception{
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getFeatures().add(new LoggingFeature());
        factory.setAddress("http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService");
        CxfDemoService client = factory.create(CxfDemoService.class);
        Person p = new Person();
        p.setName("judy");
        p.setAge(22);
        p.setGender("female");
        client.update(p);
        client.insert(p);
//        System.out.println(client.insert(p));
	}
}
