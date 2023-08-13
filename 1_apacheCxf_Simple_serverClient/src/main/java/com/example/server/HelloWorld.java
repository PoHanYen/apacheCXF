package com.example.server;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface HelloWorld{
	//@WebParam 指定soap資料的欄位名稱<text>，不定則用<arg0>
    String sayHi(@WebParam(name = "text") String text);
}
