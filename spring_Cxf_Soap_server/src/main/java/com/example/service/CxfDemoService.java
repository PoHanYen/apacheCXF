package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace="http://testService.example.com/")
public interface CxfDemoService{
	public Person insert(@WebParam(name="Person")Person person);
	public Person update(@WebParam(name="Person")Person person);
}
