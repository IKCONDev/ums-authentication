package com.ikn.ums.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping // later we will change it to post
	public ResponseEntity<String> authenticate(){
		
		return new ResponseEntity("success",HttpStatus.OK);
	}

}
