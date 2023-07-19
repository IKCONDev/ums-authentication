package com.ikn.ums.service.impl;

import java.util.ArrayList;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ikn.ums.dto.UserDetailsDto;
import com.ikn.ums.entity.UserDetailsEntity;
import com.ikn.ums.repository.UserRepository;
import com.ikn.ums.service.IUsersService;

@Service
public class UsersServiceImpl implements IUsersService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetailsDto getUserDetailsByUsername(String email) {
		UserDetailsEntity loadedUser = userRepo.findByEmail(email);
		if (loadedUser == null)
			throw new UsernameNotFoundException("User with " + email + " does not exist");
		ModelMapper mapper = new ModelMapper();
		// mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper.map(loadedUser, UserDetailsDto.class);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsEntity loadedUser = userRepo.findByEmail(username);
		System.out.println(loadedUser);
		if (loadedUser == null)
			throw new UsernameNotFoundException("User does not exists");
		return new User(loadedUser.getEmail(), loadedUser.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Transactional
	@Override
	public int generateOtpForUser(String userName) {
		Random r = new Random();
		boolean flag = false;
		Integer otp = r.nextInt(999999);
		String otpCode = otp.toString();
		try {
			// execute if otp doesnt meet requirements
			if (!(Integer.parseInt(otpCode) > 0 && otpCode.length() == 6)) {
				otp = r.nextInt();
				userRepo.saveOtp(userName, otp);
				System.out.println(otp);
				return otp;
			}
			userRepo.saveOtp(userName, otp);
			System.out.println(otp);
			return otp;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int validateUserOtp(String email, String otp) {
		int otpCode = Integer.parseInt(otp);
		int count = userRepo.validateUserOtp(email, otpCode);
		return count;
	}

}
