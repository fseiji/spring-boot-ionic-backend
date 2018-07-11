package com.fseiji.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fseiji.cursomc.security.JWTUtil;
import com.fseiji.cursomc.security.UserSS;
import com.fseiji.cursomc.services.UserService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jWTUtil; 
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jWTUtil.generateToken(user.getUsername());
		response.addHeader("Authorization ", "Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
