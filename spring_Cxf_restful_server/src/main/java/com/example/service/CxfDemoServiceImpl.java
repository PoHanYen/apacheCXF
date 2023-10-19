package com.example.service;

import com.example.bean.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON) // return response in json, can minimize scope to method
@Consumes(MediaType.APPLICATION_XML) // receive request in json, can minimize scope to method
public class CxfDemoServiceImpl implements CxfDemoService{
	@Override
	@POST
	@Path("/create")
	public Person insert(Person person){
		System.out.println(person);
		return person;
	}
	@Override
	@PUT
	@Path("/update")
	public Person update(Person person){
		System.out.println(person);
		return person;
	}
	@Override
	@DELETE
	@Path("/delete")
	public String delete(Person person){
		System.out.println(person);
		ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json=null;
		try{
			json=ow.writeValueAsString(true);
		}catch(JsonProcessingException e){
			e.printStackTrace();
		}
		return json;
	}
	@Override
	@GET
	@Path("/query/{name}")
	public Person query(@PathParam("name") String name){
		Person person=new Person();
		person.setName(name);
		person.setGender("male");
		person.setAge(18);
		System.out.println(person);
		return person;
	}
	@Override
	@GET
	@Path("/hello")
	public String hello(){
		return "helloWorld";
	}
}
