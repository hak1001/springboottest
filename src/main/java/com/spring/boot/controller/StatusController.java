package com.spring.boot.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning(HttpServletRequest request) {
    	Enumeration<String> em = request.getHeaderNames();
    	 
        while(em.hasMoreElements()){
            String name = em.nextElement() ;
            String val = request.getHeader(name) ;
            System.out.println(name + " : " + val) ;
        }

        return "I'm Alive!";
    }
}
