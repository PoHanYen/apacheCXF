package com.example.service;

import javax.jws.WebService;

import com.example.bean.Person;

@WebService
public interface CxfDemoService{
	public String insert(Person person);
	public String update(Person person);
}
