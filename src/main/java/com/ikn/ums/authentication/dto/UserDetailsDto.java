package com.ikn.ums.authentication.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
	
	    private String id;
	    private String firstName;
	    private String lastName;
	    private String designation;
	    private String department;
	    private String email;
	    private String userRole;
	    public boolean twoFactorAuthentication;
		
}