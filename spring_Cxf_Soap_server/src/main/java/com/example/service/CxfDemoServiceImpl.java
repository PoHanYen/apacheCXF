package com.example.service;

import com.example.bean.Person;

public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	public Person insert( Person person){
		System.out.println(person);
		return person;
	}
	@Override
	public Person update(Person person){
		System.out.println(person);
		return person;
	}
}
