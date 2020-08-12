package com.excilys.computerDatabase.restControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerDatabase.models.User;
import com.excilys.computerDatabase.security.JwtTokenUtil;
import com.excilys.computerDatabase.services.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AuthentificationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@PostMapping("login")
	public ResponseEntity<String> login( @RequestBody Map<String, String> log) {
		try {
			authenticate(log.get("login"),log.get("password"));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
		}

		final UserDetails userDetails = userService
				.loadUserByUsername(log.get("login"));
		
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(token);
	}
	@GetMapping(value = "getCurrentUser", produces = "application/json" )
	public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader (name="Authorization") String token) {
		 Map<String, Object> response = new HashMap<>();
		String username = jwtTokenUtil.getUsernameFromToken(token.replace("Bearer ", ""));
		response.put("username", username);
		response.put("role", userService.loadUserByUsername(username).getAuthorities());
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("changePassword")
	public ResponseEntity<String> changePassword( @RequestBody Map<String, String> log) {
		try {
			authenticate(log.get("login"),log.get("password"));
			if( userService.updatePassword(log.get("login"), passwordEncoder.encode(log.get("newPassword")))) {
				return ResponseEntity.ok("{\"sucess\" : \"password updated\"}");
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
			}
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid credentials");
		}
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register( @RequestBody Map<String, String> log) {
		User user = new User(log.get("login"));
		user.setPassword(passwordEncoder.encode(log.get("password")));
		try {
			user = userService.createUser(user);
		}catch(UnexpectedRollbackException ur) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid username");
		}
		return ResponseEntity.ok("{\"success\": \"user created\"}");
	}

	

	private void authenticate(String username, String password)
			throws Exception {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username,
						password));

	}
}
