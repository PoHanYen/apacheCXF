package com.example.client;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.example.bean.Person;
import com.example.service.CxfDemoService;

public class Client{
	private Client(){
	}
	public static void main(String[] args) throws Exception{
		JaxWsProxyFactoryBean factory=new JaxWsProxyFactoryBean();
		factory.getFeatures().add(new LoggingFeature());
		factory.setAddress("http://localhost:8081/spring_Cxf_Soap_server/cxf/CxfDemoService");
		CxfDemoService client=factory.create(CxfDemoService.class);
		Person p = new Person();
		p.setName("haha");
		p.setGender("female");
		p.setAge(123);
		Person response = client.insert(p);
		System.out.println(response);
	}
}
