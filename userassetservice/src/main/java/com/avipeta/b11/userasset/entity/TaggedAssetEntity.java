package com.avipeta.b11.userasset.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "taggedassets")
@EqualsAndHashCode
@Setter
@Getter
public class TaggedAssetEntity {
	
	@NotEmpty(message = "Asset title name may not be empty")
	private String title;
	
	@NotEmpty(message = "Asset Description name may not be empty")
	private String desrciption;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(unique = true)
	@NotEmpty(message = "bondedid name may not be empty")
	private String bondedid;
	
	@NotNull(message = "Remote assetid may not be empty")
	@Column(unique = true)
	private Long remoteassetid;
	
	@JsonIgnore //Very important to avoid recursion in making json
	@ManyToOne
	@JoinColumn
	private ActiveUserEntity taggeduser;
	
}
