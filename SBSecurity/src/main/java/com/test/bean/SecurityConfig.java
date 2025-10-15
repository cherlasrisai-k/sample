package com.test.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.test.filter.JwtFilter;

@Configuration
public class SecurityConfig {
	
	//private final PasswordEncoder passwordEncoder;
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
//	public SecurityConfig(PasswordEncoder passwordEncoder) {
//		//super();
//		this.passwordEncoder = passwordEncoder;
//	}

    @Bean
    public JwtFilter jwtFilter() {
    	return new JwtFilter();
    }

	@Bean
	public UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
			User.withUsername("user")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
				.build(),
			User.withUsername("admin")
				.password(passwordEncoder().encode("admin123"))
				.roles("ADMIN")
				.build()
			);
	}
	
	@Bean
	public AuthenticationProvider authProvider(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,JwtFilter jwtFilter) throws Exception{
		http.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(
				auth -> auth
			.requestMatchers("/auth/l")
				.permitAll()
			.requestMatchers("/api/user")
				.hasRole("USER")
			.requestMatchers("/api/admin")
				.hasRole("ADMIN")
			
				
				
				.anyRequest()
				.authenticated()
		).addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

}
