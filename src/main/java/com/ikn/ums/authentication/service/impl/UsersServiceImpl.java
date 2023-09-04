package com.ikn.ums.authentication.service.impl;

import java.util.ArrayList;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ikn.ums.authentication.VO.EmployeeVO;
import com.ikn.ums.authentication.dto.UserDetailsDto;
import com.ikn.ums.authentication.entity.UserDetailsEntity;
import com.ikn.ums.authentication.repository.UserRepository;
import com.ikn.ums.authentication.service.IUsersService;
import com.ikn.ums.authentication.utils.EmailService;

@Service
public class UsersServiceImpl implements IUsersService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public EmployeeVO getUserDetailsByUsername(String email) {

		/*
		 * // old implementation UserDetailsEntity loadedUser =
		 * userRepo.findByEmail(email); if (loadedUser == null) throw new
		 * UsernameNotFoundException("User with " + email + " does not exist");
		 * ModelMapper mapper = new ModelMapper();
		 * mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		 * return mapper.map(loadedUser, UserDetailsDto.class);
		 */
		// communicate with Employee microservice and get the employee object

		ResponseEntity<EmployeeVO> response = restTemplate
				.getForEntity("http://UMS-EMPLOYEE-SERVICE/employees/" + email, EmployeeVO.class);
		EmployeeVO employeeDetails = response.getBody();
		if (employeeDetails == null)
			throw new UsernameNotFoundException("User with " + email + " does not exist");
		return employeeDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ResponseEntity<EmployeeVO> response = restTemplate
				.getForEntity("http://UMS-EMPLOYEE-SERVICE/employees/" + username, EmployeeVO.class);
		EmployeeVO employeeDetails = response.getBody();
		System.out.println(employeeDetails);
		if (employeeDetails == null)
			throw new UsernameNotFoundException("User does not exists");
		return new User(employeeDetails.getEmail(), employeeDetails.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Transactional
	@Override
	public Integer generateOtpForUser(String userName) {
		Random r = new Random();
		Integer otp = 0;
		try {
			for (int i = 0; i < r.nextInt(999999); i++) {
				System.out.println("executed " + i);
				otp = r.nextInt(999999);
				if (otp > 000000 && otp < 999999) {
					userRepo.saveOtp(userName, otp);
					String textBody = "Hi, your secret password reset code is " + otp + "\r\n" + "\r\n" + ""
							+ "Please dont share it with anyone" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + ""
							+ "Please dont reply to this message as this is an automated email generated by system";
					emailService.sendMail(userName, "Reset Password Otp", textBody);
					break;
				}
			}
			return otp;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer validateUserOtp(String email, String otp) {
		int otpCode = Integer.parseInt(otp);
		int count = userRepo.validateUserOtp(email, otpCode);
		return count;
	}

	@Override
	@Transactional
	public Integer updatePasswordforUser(String email, CharSequence newRawPassword) {
		String encodedPassword = passwordEncoder.encode(newRawPassword);
		int updateStatus = userRepo.updatePassword(email, encodedPassword);
		return updateStatus;
	}

	@Override
	public Integer validateEmailAddress(String email) {
		int value = userRepo.validateEmail(email);
		return value;
	}

	@Transactional
	@Override
	public Integer updateUserTwoFactorAuthStatus(String email, boolean isOn) {
		return userRepo.updateTwofactorAuthenticationStatus(email, isOn);
	}

}