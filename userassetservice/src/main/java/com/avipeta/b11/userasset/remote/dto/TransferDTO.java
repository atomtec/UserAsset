package com.avipeta.b11.userasset.remote.dto;

import javax.validation.Valid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class TransferDTO {
	
	@Valid
	private long adminid;
	
	@Valid
	private long fromuserid;
	
	@Valid
	private long touserid;
	
	@Valid
	private long assetid;

}
