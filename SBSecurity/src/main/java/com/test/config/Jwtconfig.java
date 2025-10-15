package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.token.JwtUtil;

@Configuration
public class Jwtconfig {
	
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}
	
}
