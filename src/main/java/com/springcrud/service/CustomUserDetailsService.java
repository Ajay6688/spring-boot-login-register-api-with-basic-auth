package com.springcrud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springcrud.entity.User;
import com.springcrud.repository.UserRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	private UserRepository userRepo;
	
	public CustomUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("inside loadUserByUsername");
		User user = userRepo.findByUsername(username)
				.orElseThrow(()->new UsernameNotFoundException("user not found username"));
		System.out.println(user);
		
		return new User(user.getUsername() , user.getPassword());
	}
	
}
