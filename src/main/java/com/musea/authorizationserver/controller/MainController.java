package com.musea.authorizationserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<User> signup(@RequestBody User newUser) {
		
		Pattern emailPattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		if(
			Objects.isNull(newUser.getEmail()) ||
			newUser.getEmail().isBlank() ||
			newUser.getEmail().isEmpty() ||
			!emailPattern.matcher(newUser.getEmail()).matches() ||
			Objects.isNull(newUser.getUsername()) ||
			newUser.getUsername().isBlank() ||
			newUser.getUsername().isEmpty() ||
			Objects.isNull(newUser.getPassword()) ||
			newUser.getPassword().isBlank() ||
			newUser.getPassword().isEmpty()
		) {
			return ResponseEntity.status(400).build();
		}
		
		User user = userService.fetchUserByEmail(newUser.getEmail());
		if(user==null) {
			newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
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
			return ResponseEntity.status(400).build();
		}
	}

}
