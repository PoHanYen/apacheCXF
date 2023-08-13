package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;

@WebService(targetNamespace="http://example.com/access/find")
public interface AccessPersonService{
	Person find(String id);
}
