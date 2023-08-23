package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;
import jakarta.ws.rs.PathParam;

public interface CxfDemoService{
	public Person insert(Person person);
	public Person update(Person person);
	public String delete(Person person);
	public Person query(String name);
	public String hello();
}
