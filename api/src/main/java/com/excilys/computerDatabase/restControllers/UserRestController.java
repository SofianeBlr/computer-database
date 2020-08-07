package com.excilys.computerDatabase.restControllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.models.User;
import com.excilys.computerDatabase.security.JwtTokenUtil;
import com.excilys.computerDatabase.services.UserService;


@RestController
@CrossOrigin
@RequestMapping("auth")
public class UserRestController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@PostMapping("login")
	public ResponseEntity<String> login( @RequestBody Map<String, String> log) {
		try {
			authenticate(log.get("login"),log.get("password"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(log.get("login"));

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register( @RequestBody Map<String, String> log) {
		User user = new User(log.get("login"));
		user.setPassword(passwordEncoder.encode(log.get("password")));

		user = userDetailsService.createUser(user);
			
		
		if (user==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
		}
		return ResponseEntity.ok("{success: \"user created\"}");
	}

	

	private void authenticate(String username, String password)
			throws Exception {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username,
						password));

	}
}
