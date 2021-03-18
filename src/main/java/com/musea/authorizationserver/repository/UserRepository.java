package com.musea.authorizationserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musea.authorizationserver.model.User;


public interface UserRepository extends JpaRepository<User,Integer> {

	Optional<User> findByEmail(String email);

}
