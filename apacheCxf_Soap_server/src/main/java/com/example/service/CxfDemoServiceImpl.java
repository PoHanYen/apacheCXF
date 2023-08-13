package com.example.service;

public class CxfDemoServiceImpl implements CxfDemoService{
	public String hello(String name){
		return "hello "+name;
	}
}
