package com.ikn.ums.authentication.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ikn.ums.authentication.VO.EmployeeVO;
import com.ikn.ums.authentication.dto.UserDetailsDto;

public interface IUsersService extends UserDetailsService {
	
	EmployeeVO getUserDetailsByUsername(String userName);
	Integer generateOtpForUser(String userName);
	Integer validateUserOtp(String email, String otp);
	Integer updatePasswordforUser(String email, CharSequence newRawPassword);
	Integer validateEmailAddress(String email);
	Integer updateUserTwoFactorAuthStatus(String email, boolean isOn);
	
}
