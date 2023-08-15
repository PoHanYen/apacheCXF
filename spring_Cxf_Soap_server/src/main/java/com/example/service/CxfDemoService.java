package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface CxfDemoService{
	@WebMethod(operationName="insert")
	public Person insert(@WebParam(name="Person") Person person);
	@WebMethod(operationName="update")
	public Person update(@WebParam(name="Person") Person person);
}
