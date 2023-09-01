package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace="http://testService.example.com/")
public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	public Person insert(@WebParam(name="Person")Person person){
		System.out.println(person);
		return person;
	}
	@Override
	public Person update(@WebParam(name="Person")Person person){
		System.out.println(person);
		person.setName("judy");
		person.setGender("male");
		person.setAge(22);
		return person;
	}
}
