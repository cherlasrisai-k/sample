package com.test.hellocontroller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class helloController {
	@GetMapping("/api/user")
	public String UserAccess() {
		return "Hello user! you are authenticated";
	}
	@GetMapping("/api/admin")
	public String AdminAccess() {
		return "Hello Admin! you are authenticated";
	}
	
	//@PreAuthorize("hasRole('USER')")
	@GetMapping("/api/admin1")
	public String AdminAccess1() {
		return "Hello Admin! you are authenticated";
	}
}
