package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace="http://service.example.com/")
public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	public String insert(@WebParam(name="person")Person person){
		System.out.println(person);
		return "hello";
	}
	@Override
	public String update(@WebParam(name="person")Person person){
		System.out.println(person);
		return "hello";
	}
}
