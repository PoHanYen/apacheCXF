package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;
import jakarta.ws.rs.PathParam;

@WebService
public interface CxfDemoService{
	public Person insert(@PathParam("person") Person person);
	public Person update(@PathParam("person") Person person);
	public String delete(@PathParam("person") Person person);
	public Person query(@PathParam("name") String name);
	public String hello();
}
