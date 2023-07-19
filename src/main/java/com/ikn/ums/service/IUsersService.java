package com.ikn.ums.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ikn.ums.dto.UserDetailsDto;

public interface IUsersService extends UserDetailsService {
	
	UserDetailsDto getUserDetailsByUsername(String userName);
	int generateOtpForUser(String userName);
	int validateUserOtp(String email, String otp);

}
