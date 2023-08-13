package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;

@WebService(endpointInterface="com.example.service.AccessPersonService")
public class AccessPersonServiceImpl implements AccessPersonService{
	@Override
	public Person find(String id){
		Person p=new Person();
		p.setId("1");
		p.setName("john");
		p.setGender("male");
		p.setAge(32);
		return p;
	}
}
