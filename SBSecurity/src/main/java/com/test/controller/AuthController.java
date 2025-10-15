package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.token.JwtUtil;

import lombok.Data;
 
@Data
class AuthRequest{
	private String username;
	private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
@RestController
@RequestMapping("/auth")
public class AuthController {
 
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@PostMapping("/l")
	public String login(@RequestBody AuthRequest request){
		//System.out.println("Login request received for " + request.getUsername());

		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtUtil.generateToken(request.getUsername());
		}
		return "Not authenticated";
	}
 
}