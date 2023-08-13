package com.example.service;

import javax.jws.WebService;

@WebService
public interface CxfDemoService{
	String hello(String name);
}
