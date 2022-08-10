package com.avipeta.b11.userasset.remote.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@EqualsAndHashCode
public class AssetDTO {

	@NotNull(message = "id may not be null")
	private Long id;
	
	@NotEmpty(message = "Asset title name may not be empty")
	private String title;
	
	@NotEmpty(message = "Asset Description name may not be empty")
	private String desrciption;
	
	@Valid
	private Boolean isinuse = false;
	
	@Valid
	private Boolean isreleased = false;

	@Valid
	private Date addeddate = null;
	
	@NotEmpty(message = "bondedid name may not be empty")
	private String bondedid;
	

	private Date releasedate = null;
	
	
}
