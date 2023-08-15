package com.example.bean;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Person")
public class Person implements Serializable{
	private static final long serialVersionUID=1L;
	@XmlElement(name="Name")
	private String name;
	@XmlElement(name="Gender")
	private String gender;
	@XmlElement(name="Age")
	private Integer age;

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getGender(){
		return gender;
	}
	public void setGender(String gender){
		this.gender=gender;
	}
	public Integer getAge(){
		return age;
	}
	public void setAge(Integer age){
		this.age=age;
	}
	@Override
	public String toString(){
		return "Person [name="+name+", gender="+gender+", age="+age+"]";
	}
}
