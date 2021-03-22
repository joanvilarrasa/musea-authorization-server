package com.musea.authorizationserver;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
class MuseaAuthorizationServerApplicationTests {
	
	@Autowired
    private TestRestTemplate template;

    @Test
    public void givenValidCredentialsRetrieveToken() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("grant_type", "password");
    	map.add("username", "prova@prova.com");
    	map.add("password", "provapass");
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	ResponseEntity<Object> response = template.withBasicAuth("mobile", "mobilePass")
    			.postForEntity("/oauth/token", request , Object.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void givenInvalidUsernameCredentialsReturn() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("grant_type", "password");
    	map.add("username", "somethingwrong");
    	map.add("password", "provapass");
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	ResponseEntity<Object> response = template.withBasicAuth("mobile", "mobilePass")
    			.postForEntity("/oauth/token", request , Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void givenInvalidPasswordCredentialsReturn() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("grant_type", "password");
    	map.add("username", "prova@prova.com");
    	map.add("password", "somethingwrong");
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	ResponseEntity<Object> response = template.withBasicAuth("mobile", "mobilePass")
    			.postForEntity("/oauth/token", request , Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void givenInvalidGrantTypeCredentialsReturn() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("grant_type", "somethingwrong");
    	map.add("username", "prova@prova.com");
    	map.add("password", "provapass");
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	ResponseEntity<Object> response = template.withBasicAuth("mobile", "mobilePass")
    			.postForEntity("/oauth/token", request , Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void givenInvalidClientCredentialsReturn() throws Exception {
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    	MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
    	map.add("grant_type", "password");
    	map.add("username", "prova@prova.com");
    	map.add("password", "provapass");
    	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
    	ResponseEntity<Object> response = template.withBasicAuth("somethingwrong", "mobilePass")
    			.postForEntity("/oauth/token", request , Object.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}
