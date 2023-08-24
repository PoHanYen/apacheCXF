package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(endpointInterface="CxfDemoSedrvice",targetNamespace="http://testservice.example.com/")
public interface CxfDemoService{
	public Person insert(@WebParam(name="Person")Person person);
	public Person update(@WebParam(name="Person")Person person);
}
