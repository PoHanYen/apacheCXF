package com.example.springboot_webservice_soap.service;

import com.example.springboot_webservice_soap.bean.Person;

public class AccessServiceImpl implements AccessService{

    @Override
    public Person create(Person person) {
        System.out.println(person);
        return person;
    }
    @Override
    public Person update(Person person) {
        System.out.println(person);
        return person;
    }
    @Override
    public Person query(String name) {
        Person p = new Person();
        p.setName(" 張三李四");
        p.setGender("Male");
        p.setAge(12);
        System.out.println(p);
        return p;
    }
    @Override
    public String delete(Person person) {
        System.out.println(person);
        return String.valueOf(true);
    }

    @Override
    public String hello() {
        return "Hello";
    }
}
