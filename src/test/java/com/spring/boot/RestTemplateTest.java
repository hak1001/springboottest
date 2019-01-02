package com.spring.boot;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {
	RestTemplate restTemplate = new RestTemplate();
	
	@Test
	public void restTest() {
		String url = "http://127.0.0.1:8080/ping";
		String result = restTemplate.getForObject(url, String.class);
		ResponseEntity<String> result1 = restTemplate.getForEntity(url, String.class);
		HttpStatus rescode1 = restTemplate.getForEntity(url, String.class).getStatusCode();
		System.out.println("result = " + result);
		System.out.println("result1 = " + result1);
		System.out.println("rescode1 = " + rescode1);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//	    headers.add("content-type", "application/json;charset=UTF-8");
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    
	    String result2 = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	    ResponseEntity<String> result3 = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    HttpStatus rescode3 = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getStatusCode();
	    
	    restTemplate.getForObject(url, String.class);
	    System.out.println("result2 === " + result2); 
	    System.out.println("result3 === " + result3);
	    System.out.println("rescode3 === " + rescode3);
	}
}

