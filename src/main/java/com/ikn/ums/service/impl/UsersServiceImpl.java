package com.ikn.ums.service.impl;

import java.util.ArrayList;
import java.util.Random;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ikn.ums.dto.UserDetailsDto;
import com.ikn.ums.entity.UserDetailsEntity;
import com.ikn.ums.repository.UserRepository;
import com.ikn.ums.service.IUsersService;
import com.ikn.ums.utils.EmailService;

@Service
public class UsersServiceImpl implements IUsersService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
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
		Integer otp = 0;
		try {
			for(int i=0; i<r.nextInt(999999); i++) {
				System.out.println("executed "+i);
				otp = r.nextInt(999999);
				if(otp>000000 && otp<999999) {
					userRepo.saveOtp(userName, otp);
					String textBody = "Hi, your secret password reset code is "+otp+"\r\n"+"\r\n"+""
                            + "Please dont share it with anyone"+"\r\n"+"\r\n"+"\r\n"+"\r\n"+""
                            +"Please dont reply to this message as this is an automated email generated by system";
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
	public int validateUserOtp(String email, String otp) {
		int otpCode = Integer.parseInt(otp);
		int count = userRepo.validateUserOtp(email, otpCode);
		return count;
	}

	@Override
	@Transactional
	public int updatePasswordforUser(String email, CharSequence newRawPassword) {
		String encodedPassword =  passwordEncoder.encode(newRawPassword);
		int updateStatus = userRepo.updatePassword(email, encodedPassword);
		return updateStatus;
	}

	@Override
	public int validateEmailAddress(String email) {
	    int value = userRepo.validateEmail(email);
		return value;
	}

}
