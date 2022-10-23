
/*
 * A controller class that creates a rest controller to handle all authentication related requests
 * 
 */

package com.ccms.hris.controllers;


import com.ccms.hris.models.AuthenticationRequest;
import com.ccms.hris.models.AuthenticationResponse;
import com.ccms.hris.models.ResponseWrapper;
import com.ccms.hris.models.dto.UserDto;
import com.ccms.hris.models.entities.User;
import com.ccms.hris.services.AuthService;
import com.ccms.hris.services.UserService;
import com.ccms.hris.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private AuthService userDetailsService;
	

	@Autowired
	private UserService userService;

	@CrossOrigin
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		
		User correspondingUser = userService.findUserByEmail(userDetails.getUsername());

		UserDto correspondingUserDto = new UserDto(correspondingUser.getUserId(), correspondingUser.getEmail(), correspondingUser.getFirstName(), correspondingUser.getLastName(),  correspondingUser.getUserStatus(), correspondingUser.getRoles(), correspondingUser.getDesignation());
		
		


		ResponseWrapper body = new ResponseWrapper(new AuthenticationResponse(jwt,correspondingUserDto), "success", "fetched");
		
		return ResponseEntity.status(HttpStatus.OK)
				.body(body);
			
	}

}
