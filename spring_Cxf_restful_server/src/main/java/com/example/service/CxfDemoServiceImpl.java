package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@WebService(targetNamespace="http://service.example.com/")
public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	@POST
	@Path("/{person}")
	public Person insert(@PathParam("person")Person person){
		System.out.println(person);
		return person;
	}
	@Override
	@PUT
	@Path("update/{person}")
	public Person update(@PathParam("person")Person person){
		System.out.println(person);
		return person;
	}
	@Override
	@DELETE
	@Path("/delete/{person}")
	public boolean delete(@PathParam("person")Person person){
		System.out.println(person);
		return true;
	}
	@Override
	@GET
	@Path("/query/{name}")
	public Person query(@PathParam("name")String name){
		Person person=new Person();
		person.setName(name);
		person.setGender("male");
		person.setAge(18);
		System.out.println(person);
		return person;
	}
}
