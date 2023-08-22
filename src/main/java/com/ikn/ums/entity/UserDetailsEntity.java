package com.ikn.ums.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details_tab")
public class UserDetailsEntity{
	
	//user login properties
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id_col")
	public Integer id;
	
	@Column(name = "user_fstn_col")
	public String firstName;
	
	@Column(name = "user_lstn_col")
	public String lastName;
	
	@Column(name = "user_email_col")
	public String email;
	
	@Column(name = "user_encypwd_col")
	public String encryptedPassword;
	
	@Column(name = "user_role_col")
	public String userRole;
	
	@Column(name = "user_userid_col")
	public String userId;
	
	@Column(name = "user_otp_code")
	public int otpCode;

	
	//setters and getters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	//toString
	@Override
	public String toString() {
		return "UserDetailsModel [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", encryptedPassword=" + encryptedPassword + ", userRole=" + userRole + ", userId=" + userId + "]";
	}
	
	
}
