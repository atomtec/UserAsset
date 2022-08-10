package com.avipeta.b11.asset.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "asset")
public class AssetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Date addeddate = Calendar.getInstance().getTime();
	
	@Column(unique = true)
	@NotEmpty(message = "bondedid name may not be empty")
	private String bondedid;
	

	private Date releasedate = null;
	
	
}
