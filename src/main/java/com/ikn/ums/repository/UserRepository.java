package com.ikn.ums.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ikn.ums.entity.UserDetailsEntity;

@Repository
public interface UserRepository extends JpaRepository<UserDetailsEntity, Integer> {
	
	UserDetailsEntity findByEmail(String email);
	
	@Modifying
    @Query("Update UserDetailsEntity SET user_otp_code=:otp WHERE user_email_col=:username")
	void saveOtp(String username, int otp);
    
    @Query("SELECT COUNT(*) FROM UserDetailsEntity WHERE user_email_col=:email and user_otp_code=:otp")
    Integer validateUserOtp(String email, int otp);

}
