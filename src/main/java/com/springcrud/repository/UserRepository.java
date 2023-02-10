package com.springcrud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springcrud.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
