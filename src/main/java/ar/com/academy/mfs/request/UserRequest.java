package ar.com.academy.mfs.request;

import ar.com.academy.mfs.model.Role;

public class UserRequest {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private Role role;
	private String email;
	private int phoneNumber;
	private String documentType;
	private int documentNumber;
	private String address;
	int role_id;
	
	public UserRequest() {
		
	}
	
	public UserRequest(String username, String password, String firstname, String lastname, Role role, String email,
			int phoneNumber, String documentType, int documentNumber, String address) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public int getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(int documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getRole_id() {
		return role_id;
	}
}
