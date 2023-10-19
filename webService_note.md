# Web Service

## Web Service 定義

- 一種跨程式語言和作業系統平台的遠端呼叫技術。
- 使不同系統/應用服務透過特定的網路通訊協定定義溝通與資料交換的方式，使用這種方式讓不同的系統可以忽略程式語言，技術與平台之間的差異。
- 市面上較流行的兩種 Web Service:
  - SOAP (Simple Object Access Protocal)
  - REST (Representationl State Transfer)

## main方法模擬 web service/client

### 環境

- 建立maven project
- jdk: oracle jdk-17.0.1
- Server: tomcat 10.1

### mavan配置

``` xml
  <!--cxf-->
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-core</artifactId>
   <version>4.0.2</version> <!-- Use the latest version -->
  </dependency>
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-frontend-jaxws</artifactId>
   <version>4.0.2</version> <!-- Use the latest version -->
  </dependency>
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-transports-http</artifactId>
   <version>4.0.2</version> <!-- Use the latest version -->
  </dependency>
  <dependency>
   <groupId>jakarta.xml.ws</groupId>
   <artifactId>jakarta.xml.ws-api</artifactId>
   <version>3.0.1</version> <!-- Use the latest version -->
  </dependency>
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-transports-http-jetty</artifactId>
   <version>4.0.2</version> <!-- Use the latest version -->
  </dependency>
```

 PS: 因沒有使用物件做資料傳輸(沒做binding)，所以有些jar並未引入。  
 </br>

### 創建一個web service interface

- 用@webService annotation標註此interface為client呼叫操作的"入口"程式。
- @WebParam 可指定接收訊息時，接收參數的名稱。若不指定，則預設arg0, arg1....
  
``` java
package com.example.server;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface HelloWorld{
 //@WebParam 指定soap資料的欄位名稱<text>，不定則用<arg0>
    String sayHi(@WebParam(name = "text") String text);
}

```

### 創建class實作interface

- 實作interface功能，當server收到訊息，功能真正執行的內容。
  
``` java
package com.example.server;

public class HelloWorldImpl implements HelloWorld{
 public String sayHi(String text){
  System.out.println("sayHi called");
  return "Hello "+text;
 }
}
```

### Mock Server

- 使用jetty去做一個模擬server，設定URL與。
- main方法啟動執行完後，server也會關閉，所以中間讓 thread sleep存活五分鐘。

``` java
package com.example.server;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

@SuppressWarnings("deprecation")
public class Server{
 protected Server() throws Exception{
  System.out.println("Starting Server");
  HelloWorldImpl implementor=new HelloWorldImpl();
  JaxWsServerFactoryBean svrFactory=new JaxWsServerFactoryBean();
  svrFactory.setServiceClass(HelloWorld.class);
  svrFactory.setAddress("http://localhost:9000/helloWorld");
  svrFactory.setServiceBean(implementor);
  svrFactory.getFeatures().add(new LoggingFeature());
  svrFactory.create();
 }
 public static void main(String[] args) throws Exception{
  new Server();
  System.out.println("Server ready...");
  Thread.sleep(5*60*1000);
  System.out.println("Server exiting");
  System.exit(0);
 }
}
```

### Mock client

- 在server啟動後，可以執行client。發送request給server。
  
``` java
package com.example.client;

import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.example.server.HelloWorld;

@SuppressWarnings("deprecation")
public class Client{
 public static void main(String[] args) throws Exception{
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getFeatures().add(new LoggingFeature());
        factory.setAddress("http://localhost:9000/helloWorld");
        HelloWorld client = factory.create(HelloWorld.class);
        System.out.println(client.sayHi("World"));
 }
}
```

### Log

- 從下方log可以看出SOAP訊息傳輸的xml格式，由一個Envelope包住Body，Body中間會有執行的方法，方法再包住該方法接收或吐回的訊息內容。

- Outbound Message的payload是client送出的訊息，Inbound Message的payload是server回覆的訊息。

- 從Outbound Message可以看出，client呼叫sayHi方法，傳送了一個參數名text的參數。

> Client送出訊息

``` xml
INFO: Outbound Message
---------------------------
ID: 1
Address: http://localhost:9000/helloWorld
Encoding: UTF-8
Http-Method: POST
Content-Type: text/xml
Headers: {Accept=[*/*], SOAPAction=[""]}
Payload: 
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
 <soap:Body>
  <ns2:sayHi xmlns:ns2="http://server.example.com/">
   <text>World</text>
  </ns2:sayHi>
 </soap:Body>
</soap:Envelope>
--------------------------------------
```

> Server回覆的soap訊息。
  
``` xml
8月 24, 2023 4:44:39 下午 org.apache.cxf.services.HelloWorldService.HelloWorldPort.HelloWorld
INFO: Inbound Message
----------------------------
ID: 1
Response-Code: 200
Encoding: UTF-8
Content-Type: text/xml;charset=utf-8
Headers: {content-type=[text/xml;charset=utf-8], date=[Thu, 24 Aug 2023 08:44:39 GMT], server=[Jetty(11.0.15)], transfer-encoding=[chunked]}
Payload: 
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
 <soap:Body>
  <ns2:sayHiResponse xmlns:ns2="http://server.example.com/">
   <return>Hello World</return>
  </ns2:sayHiResponse>
 </soap:Body>
</soap:Envelope>
--------------------------------------
```

## SOAP Web Service

- 建立maven project。

### Maven

- 先引入apache cxf相關的jar。

```xml
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-frontend-jaxws</artifactId>
   <version>4.0.2</version>
  </dependency>
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-transports-http</artifactId>
   <version>4.0.2</version>
  </dependency>
  <dependency>
   <groupId>org.apache.cxf</groupId>
   <artifactId>cxf-rt-transports-http-jetty</artifactId>
   <version>3.4.0</version>
  </dependency>
```

- 引入web service所需要的jar。這些在java 17被移除了，需要自行加入。

```xml
  <!-- cxf jakarta-->
  <dependency>
   <groupId>jakarta.annotation</groupId>
   <artifactId>jakarta.annotation-api</artifactId>
   <version>2.1.1</version>
  </dependency>
  <dependency>
   <groupId>jakarta.xml.ws</groupId>
   <artifactId>jakarta.xml.ws-api</artifactId>
   <version>4.0.0</version>
  </dependency>
  <dependency>
   <groupId>jakarta.xml.soap</groupId>
   <artifactId>jakarta.xml.soap-api</artifactId>
   <version>3.0.0</version>
  </dependency>
  <dependency>
   <groupId>jakarta.servlet</groupId>
   <artifactId>jakarta.servlet-api</artifactId>
   <version>6.0.0</version>
   <scope>provided</scope>
  </dependency>
  <dependency>
   <groupId>jakarta.xml.bind</groupId>
   <artifactId>jakarta.xml.bind-api</artifactId>
   <version>4.0.0</version>
  </dependency>
  <dependency>
   <groupId>org.glassfish.jaxb</groupId>
   <artifactId>jaxb-runtime</artifactId>
   <version>4.0.3</version>
  </dependency>
  <!-- cxf jakarta-->
```

- 引入所需 Spring jar。

```xml
  <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-web</artifactId>
   <version>6.0.11</version>
  </dependency>
  <dependency>
   <groupId>org.springframework</groupId>
   <artifactId>spring-context</artifactId>
   <version>6.0.11</version>
  </dependency>
```

### 建立用於傳輸的物件 Person

- 用 ***@XmlRootElement 與 @XmlElement*** 做物件與 xml 的轉換關聯。
- JAXB binding同時使用 field 跟properties 做物件與xml的mapping，會造成
  Caused by: org.glassfish.jaxb.runtime.v2.runtime.IllegalAnnotationsException: 3 counts of IllegalAnnotationExceptions。有兩種方式預防這個exception:
  - class上放 ***@XmlAccessorType(XmlAccessType.FIELD)*** ，JAXB只使用Field mapping xml。
  - 在每個setXX()上使用 ***@XmlTransient*** 阻止 JAXB透過setter mapping xml。

```java
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
// @XmlTransient
 public void setName(String name){
  this.name=name;
 }
 public String getGender(){
  return gender;
 }
// @XmlTransient
 public void setGender(String gender){
  this.gender=gender;
 }
 public Integer getAge(){
  return age;
 }
// @XmlTransient
 public void setAge(Integer age){
  this.age=age;
 }
}
```

### 建立interface

- Soap Server 需要制定一個入口接收client的傳過來的訊息，可以是 class或 interface。
- ***@WebService***可以標註在 class或 interaec, 去表示該 class繼承了 web service 或是 interface繼承 web service interface。註記在那裡會導致wsdl內容不同。
- ***@WebParam(name="")*** 定義了當收到client消息時，物件或資料的名稱。若無定義，默認使用arg0, arg1, arg2....如果client不按照定義的名稱傳輸資料，就會無法做xml to object的轉換，會有exception。

```java
package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(endpointInterface="com.example.service.CxfDemoServiceImpl",targetNamespace="http://testService.example.com/")
public interface CxfDemoService{
 public Person insert(@WebParam(name="Person")Person person);
 public Person update(@WebParam(name="Person")Person person);
}

```

PS: 個人認為Client只需要知道有個interface可以傳送訊息就好，不需要知道該功能的詳細內容。  
</br>

### 實作方法

- 簡單的實作收到資料，回傳資料。

``` java
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
  person.setName("judy");
  person.setGender("male");
  person.setAge(22);
  return person;
 }
}
```

### cxf.xml配置

- 設定一個endpoint，client可以透過 {專案路徑}/CxfDemoService執行 CxfDemoServiceImpl。
- 以這個案例，當TomcatServer啟動後， Server會接收任何透過 [http:localhost:8080/spring_Cxf_Soap_server/CxfDemoService](http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService) 的soap Message。Server收到message之後，執行CxfDemoServiceImpl內對應的方法。
  
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:jaxws="http://cxf.apache.org/jaxws"
       xsi:schemaLocation=" http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
       http://cxf.apache.org/jaxws http://cxf.apache.org/schemas/jaxws.xsd">    
    <jaxws:endpoint id="CxfDemoServiceImpl" 
    implementor="com.example.service.CxfDemoServiceImpl" 
    address="/CxfDemoService"/>
</beans>
```

### web.xml配置

- 配置一個ConextLoaderListener接收訊息，再由CXFServlet執行訊息的處理。
- listener按照cxf.xml的接收訊息。

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:web="http://xmlns.jcp.org/xml/ns/javaee">
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>/WEB-INF/cxf.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
    <servlet-name>CXFServlet</servlet-name>
    <servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>CXFServlet</servlet-name>
    <url-pattern>/*</url-pattern>
  </servlet-mapping>
</web-app>
```

### wsdl

- Server啟動後，可以透過這個URL去看到web service的wsdl layout， [http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService?wsdl](http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService?wsdl)。

- Client拿著這個wsdl在自己肚子裡生成對應的interface。

``` xml
<wsdl:definitions
 xmlns:xsd="http://www.w3.org/2001/XMLSchema"
 xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
 xmlns:tns="http://service.example.com/"
 xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
 xmlns:ns2="http://schemas.xmlsoap.org/soap/http"
 xmlns:ns1="http://testService.example.com/"
 name="CxfDemoServiceImplService"
 targetNamespace="http://service.example.com/">
 <wsdl:import
  location="http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService?wsdl=CxfDemoService.wsdl"
  namespace="http://testService.example.com/">
 </wsdl:import>
 <wsdl:binding name="CxfDemoServiceImplServiceSoapBinding"
  type="ns1:CxfDemoService">
  <soap:binding style="document"
   transport="http://schemas.xmlsoap.org/soap/http" />
  <wsdl:operation name="insert">
   <soap:operation soapAction="" style="document" />
   <wsdl:input name="insert">
    <soap:body use="literal" />
   </wsdl:input>
   <wsdl:output name="insertResponse">
    <soap:body use="literal" />
   </wsdl:output>
  </wsdl:operation>
  <wsdl:operation name="update">
   <soap:operation soapAction="" style="document" />
   <wsdl:input name="update">
    <soap:body use="literal" />
   </wsdl:input>
   <wsdl:output name="updateResponse">
    <soap:body use="literal" />
   </wsdl:output>
  </wsdl:operation>
 </wsdl:binding>
 <wsdl:service name="CxfDemoServiceImplService">
  <wsdl:port
   binding="tns:CxfDemoServiceImplServiceSoapBinding"
   name="CxfDemoServiceImplPort">
   <soap:address
    location="http://localhost:8080/spring_Cxf_Soap_server/CxfDemoService" />
  </wsdl:port>
 </wsdl:service>
</wsdl:definitions>
```

### Soap Message

client send

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <ns2:update xmlns:ns2="http://testService.example.com/">
            <Person>
                <ns2:Name>haha</ns2:Name>
                <ns2:Gender>female</ns2:Gender>
                <ns2:Age>123</ns2:Age>  
            </Person>
        </ns2:update>
    </soap:Body>
</soap:Envelope>
```

Server return

```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    <soap:Body>
        <ns2:updateResponse xmlns:ns2="http://testService.example.com/">
            <return>
                <ns2:Name>judy</ns2:Name>
                <ns2:Gender>male</ns2:Gender>
                <ns2:Age>22</ns2:Age>
            </return>
        </ns2:updateResponse>
    </soap:Body>
</soap:Envelope>
```

## Rest Web Service

- 實作傳輸物件
  
- 使用物件包裝傳輸資料需要使用到物件與XML互相轉換的工具xml.bind。
  
- @XmlAccessorType()用於指定object-xml binding透過方式執行，可選擇field,property, serialized。
- @XmlRootElement()設定Object轉換成xml格式後的名稱，不指定則使用預設arg0。
- @XmlElement設定Object轉換成xml格式後的欄位名稱，不指定則使用預設arg0。

```java
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
```

### 創造interface

```java
package com.example.service;

import com.example.bean.Person;

import jakarta.jws.WebService;
import jakarta.ws.rs.PathParam;

public interface CxfDemoService{
	public Person insert(Person person);
	public Person update(Person person);
	public String delete(Person person);
	public Person query(String name);
	public String hello();
}
```

### 創造class實作interface

- 依照restful風格去訂每個方法接收request的方式。比方說，使用@Get，該方法只能接受get方法過來的資料，使用@Post，該方法只能接受Post方法過來的資料。
- @Produces設定Server 發送response給client時，是使用json or xml格式。
- @Consumes設定Server 接收client request時，是使用json or xml格式。
- @Produces & @Consumes 可以設定整個class的所有方法都是用指定資料格式，也可以單一指定某個方法。

```java
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
@Consumes(MediaType.APPLICATION_JSON) // receive request in json, can minimize scope to method
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
```

## cxf.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:jaxrs="http://cxf.apache.org/jaxrs"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://cxf.apache.org/jaxrs http://cxf.apache.org/schemas/jaxrs.xsd">
	<bean id="cxf" class="org.apache.cxf.bus.spring.SpringBus" />
	<jaxrs:server id="restFulService">
		<jaxrs:serviceBeans>
			<bean class="com.example.service.CxfDemoServiceImpl" />
		</jaxrs:serviceBeans>
		<jaxrs:providers>
			<bean class="org.apache.cxf.jaxrs.provider.json.JSONProvider" />
		</jaxrs:providers>
	</jaxrs:server>
</beans>

```

## web.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/cxf.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<servlet>
		<servlet-name>CXFServlet</servlet-name>
		<servlet-class>org.apache.cxf.transport.servlet.CXFServlet</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>CXFServlet</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>
</web-app>

```