package com.ikn.ums.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ikn.ums.model.UpdatePasswordRequestModel;
import com.ikn.ums.model.ValidateOtpRequestModel;
import com.ikn.ums.service.IUsersService;

@RestController
@RequestMapping("/users")
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private IUsersService userService;
	
	@GetMapping // later we will change it to post
	public ResponseEntity<String> authenticate(){
		String encodedPWD = encoder.encode("test");
		return new ResponseEntity<String>(encodedPWD,HttpStatus.OK);
	}
	
	@PostMapping("/generate-otp/{email}")
	public ResponseEntity<?> generateAndSendOtpToUser(@PathVariable String email){
		try {
			Integer otp = userService.generateOtpForUser(email);	
			return new ResponseEntity<>(otp, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/validate-otp")
	public ResponseEntity<?> validateUserOtp(@RequestBody ValidateOtpRequestModel otpRequestModel){
		
		try {
			int count = userService.validateUserOtp(otpRequestModel.getEmail(), otpRequestModel.getOtpCode());
			return new ResponseEntity<>(count, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestModel updatePasswordModel){
		try {
			int updateStatus = userService.updatePasswordforUser(updatePasswordModel.getEmail(), 
					                                             updatePasswordModel.getConfirmPassword());
			return new ResponseEntity<>(updateStatus, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
