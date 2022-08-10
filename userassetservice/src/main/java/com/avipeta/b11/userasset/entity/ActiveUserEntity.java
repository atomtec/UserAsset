package com.avipeta.b11.userasset.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "activeusers")
@EqualsAndHashCode
@Getter
@Setter
public class ActiveUserEntity {
	
	@NotEmpty(message = "First name may not be empty")
	private String firstname;

	@NotEmpty(message = "Last name may not be empty")
	private String lastname;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "Remote id may not be empty")
	@Column(unique = true)
	private Long remoteuserid;
	

	@NotEmpty(message = "Employee id may not be empty")
	private String employeeid;

	@NotEmpty(message = "PhoneNumber may not be empty")
	private String phonenumber;

	@OneToMany(mappedBy = "taggeduser", orphanRemoval = true , cascade = CascadeType.ALL)
	private List<TaggedAssetEntity> taggedAssets = null;

}
