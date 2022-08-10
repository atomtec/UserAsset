package com.avipeta.b11.userasset.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "transferlogs")
public class LogTransferEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Asset title name may not be empty")
	private String title;
	
	@NotEmpty(message = "bondedid name may not be empty")
	private String bondedid;
	
	@NotEmpty(message = "From name may not be empty")
	private String fromusername;
	
	@NotEmpty(message = "To User name may not be empty")
	private String tousername;
	
	@NotEmpty(message = "transfered by user name")
	private String transferbyusername;
	

	@Setter(AccessLevel.PRIVATE) private Date transferDate = Calendar.getInstance().getTime();



}
