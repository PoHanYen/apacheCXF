package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@WebService
public interface CxfDemoService{
	@POST
	@Path("")
	public Person insert(@PathParam("person")Person person);
	@PUT
	@Path("update/{person}")
	public Person update(@PathParam("person")Person person);
	@DELETE
	@Path("/delete/{person}")
	public boolean delete(@PathParam("person")Person person);
	@GET
	@Path("/query/{name}")
	public Person query(@PathParam("name")String name);
}
