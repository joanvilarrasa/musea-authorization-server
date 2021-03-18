package com.musea.authorizationserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.musea.authorizationserver.model.Role;
import com.musea.authorizationserver.model.User;
import com.musea.authorizationserver.service.UserService;

@RestController
public class MainController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)	
	public String greetingGet() {
		return "hello from GET";
	}
	
	@RequestMapping(value = "/hello", method = RequestMethod.POST)	
	public String greetingPost() {
		return "hello from POST";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)	
	public String greetingSignUp() {
		return "You need to use POST brother";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<User> signup(@RequestBody User newUser) {
		User user = userService.fetchUserByEmail(newUser.getEmail());
		if(user==null) { 
			newUser.setEnabled(true);
			newUser.setAccountNonExpired(true);
			newUser.setCredentialsNonExpired(true); 
			newUser.setAccountNonLocked(true);
			
			List<Role> roles = new ArrayList<Role>();
			Role userRole = new Role();
			userRole.setId(2);
			userRole.setName("ROLE_USER");
			roles.add(userRole);
			newUser.setRoles(roles);
			
			userService.save(newUser);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(404).build();
		}
	}

}
