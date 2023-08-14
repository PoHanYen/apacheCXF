package com.example.service;

import jakarta.jws.WebService;

@WebService(targetNamespace="http://service.example.com/")
public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	public String hello(String name){
		return "hello "+name;
	}

	@Override
	public String sayGoodBye(String name){
		return "GoodBye myFriend " +name;
	}
}
