package com.springcrud.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.springcrud.entity.Role;
import com.springcrud.entity.User;
import com.springcrud.payload.LoginDto;
import com.springcrud.payload.RegisterDto;
//import com.springcrud.repository.RoleRepository;
import com.springcrud.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

	@GetMapping("/hello")
	public String sayHello() {
		
		System.out.println("in hello");
		return "hello spring boot ";
	}
	
	@GetMapping("auth/ajay")
	public String sayAjay() {
		
		System.out.println("in hello");
		return "hello spring boot by Ajay";
	}
	
	
	@Autowired
	private AuthenticationManager authManager ;
	
	@Autowired
	private UserRepository userRepo ;
	
//	@Autowired
//	private RoleRepository roleRepo ;
	
	@Autowired 
	private PasswordEncoder passwordEncoder ;
	
	@PostMapping("auth/signin")
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
		
		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
			
	        
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        
			return new ResponseEntity<>("user signed in successfully!" , HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@PostMapping("auth/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterDto registerDto){
		
		try {
			// add check for user exists in db of not 
			if(userRepo.existsByUsername(registerDto.getUsername())) {
				return new ResponseEntity<>("username already taken!" , HttpStatus.BAD_REQUEST);
			}
			
//			create user object 
			User user = new User();
			user.setName(registerDto.getName());
			user.setEmail(registerDto.getEmail());
			user.setPassword(registerDto.getPassword());
			user.setUsername(registerDto.getUsername());
			
//			Role roles = roleRepo.findByName("Role_ADMIN").get();
			
//			user.setRoles(Collections.singleton(roles));
			
			userRepo.save(user);
			
			return new ResponseEntity<>("user registered scuccessfully!" , HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

}
