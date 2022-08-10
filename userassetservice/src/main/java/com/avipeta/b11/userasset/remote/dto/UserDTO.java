package com.avipeta.b11.userasset.remote.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class UserDTO {
	
	@NotEmpty(message = "First name may not be empty")
	private String firstname;

	@NotEmpty(message = "Last name may not be empty")
	private String lastname;
	

	private Long id;
	

	@NotEmpty(message = "Employee id may not be empty")
	private String employeeid;

	@NotEmpty(message = "PhoneNumber may not be empty")
	private String phonenumber;
	
	@Valid
	private boolean isTagged = false;
	
	@Valid
	private boolean isReleased = false;
	
	
	

}
