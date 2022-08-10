package com.avipeta.b11.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "customer")
public class CustomerDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "First name may not be empty")
	String firstname;

	@NotEmpty(message = "Last name may not be empty")
	String lastname;

	@Column(unique = true)
	@NotEmpty(message = "Pan number may not be empty")
	String pannumber;

	@Column(unique = true)
	@NotEmpty(message = "Email id may not be empty")
	String emailid;

	@NotEmpty(message = "Address may not be empty")
	String address;

	@NotEmpty(message = "PhoneNumber may not be empty")
	String phonenumber;

	@OneToMany(mappedBy = "primarycustomer", orphanRemoval = true , cascade = CascadeType.ALL)
	private Set<AccountDAO> accounts = null;

	public Set<AccountDAO> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<AccountDAO> accounts) {
		if(this.accounts == null)
			this.accounts = accounts;
		else
		{
			this.accounts.retainAll(accounts);
			this.accounts.addAll(accounts);
		}
		//Why this type of assignment 
		//https://stackoverflow.com/questions/5587482/hibernate-a-collection-with-cascade-all-delete-orphan-was-no-longer-referenc
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPannumber() {
		return pannumber;
	}

	public void setPannumber(String pannumber) {
		this.pannumber = pannumber;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		return "CustomerDAO [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", emailId=" + emailid
				+ " , phonenumber=" + phonenumber + " , address=" + address + "]";
	}

}
