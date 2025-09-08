package com.app.Ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.Ecommerce.dto.AuthRequest;
import com.app.Ecommerce.dto.AuthResponse;
import com.app.Ecommerce.dto.RegisterRequest;
import com.app.Ecommerce.enums.Role;
import com.app.Ecommerce.model.User;
import com.app.Ecommerce.repository.UserRepository;
import com.app.Ecommerce.security.JwtUtils;

import ch.qos.logback.core.joran.spi.NoAutoStart;

@RestController
@RequestMapping("/auth")
public class AuthController {

	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRespository;
	
	@Autowired
	private PasswordEncoder passswordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	
	@PostMapping("/register")
	private ResponseEntity<String> register(@RequestBody RegisterRequest register){
		User user = new User();
		user.setName(register.getName());
		user.setAddress(register.getAddress());
		user.setEmailId(register.getEmail());
		user.setPassword(passswordEncoder.encode(register.getPassword()));
		user.setRole(Role.valueOf(register.getRole().toUpperCase()));
		user.setCountry(register.getCountry());
		user.setLocation(register.getLocation());
		userRespository.save(user);
		
		return ResponseEntity.ok("User Registered Successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		
		Authentication auth = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		String token = jwtUtils.generateToken(request.getEmail());
		
		return ResponseEntity.ok(new AuthResponse(token));
	
	}
	
}
