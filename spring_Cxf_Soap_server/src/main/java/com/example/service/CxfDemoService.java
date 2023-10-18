package com.example.service;

import com.example.bean.Person;

//@WebService(targetNamespace="http://testService.example.com/")
//@SOAPBinding(style=Style.RPC, use=Use.LITERAL)
public interface CxfDemoService{
	public Person insert(/*@WebParam(name="Person")*/Person person);
	public Person update(/*@WebParam(name="Person")*/Person person);
}
