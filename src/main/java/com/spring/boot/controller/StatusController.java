package com.spring.boot.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning(HttpServletRequest request) {
    	System.out.println("GET");
    	Enumeration<String> em = request.getHeaderNames();
    	 
        while(em.hasMoreElements()){
            String name = em.nextElement() ;
            String val = request.getHeader(name) ;
            System.out.println(name + " : " + val) ;
        }

        return "I'm Alive!";
    }
    
//    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @PostMapping(value = "/ping")
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning2(HttpServletRequest request, @RequestBody String txt) {
    	System.out.println("POST : " + txt);
    	Enumeration<String> em = request.getHeaderNames();
    	 
        while(em.hasMoreElements()){
            String name = em.nextElement() ;
            String val = request.getHeader(name) ;
            System.out.println(name + " : " + val) ;
        }

        return "I'm Alive!";
    }
}
