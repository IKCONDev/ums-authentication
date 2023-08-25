package com.ikn.ums.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ikn.ums.dto.UserDetailsDto;

public interface IUsersService extends UserDetailsService {
	
	UserDetailsDto getUserDetailsByUsername(String userName);
	Integer generateOtpForUser(String userName);
	Integer validateUserOtp(String email, String otp);
	Integer updatePasswordforUser(String email, CharSequence newRawPassword);
	Integer validateEmailAddress(String email);
	Integer updateUserTwoFactorAuthStatus(String email, boolean isOn);
	
}
