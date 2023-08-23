package com.example.springboot_webservice_soap.service;

import com.example.springboot_webservice_soap.bean.Person;

public interface AccessService {
    public Person create(Person person);
    public Person update(Person person);
    public Person query(String name);
    public String delete(Person person);
    public String hello();

}
