package com.musea.authorizationserver.service;

import com.musea.authorizationserver.model.User;

public interface UserService {
	
	User save(User user);
	
	User fetchUserById(int id);
	User fetchUserByEmail(String email);

}
