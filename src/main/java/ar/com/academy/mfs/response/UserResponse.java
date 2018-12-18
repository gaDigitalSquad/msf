package ar.com.academy.mfs.response;

import ar.com.academy.mfs.model.Role;
import ar.com.academy.mfs.model.User;

public class UserResponse {
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private long role_id;
	private int phoneNumber;
	private String documentType;
	private int documentNumber;
	private String address;	
	private long zone_id;
	private String turn;

	
	public UserResponse() {
	}
	
	public UserResponse(User user) {
		super();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.firstname = user.getFirstname();
		this.lastname = user.getLastname();
		this.role_id = user.getRole_id();
		this.phoneNumber = user.getPhoneNumber();
		this.documentType = user.getDocumentType();
		this.documentNumber = user.getDocumentNumber();
		this.turn = user.getTurn();
		this.zone_id = user.getZone_id();
	}

	


	public UserResponse(String username, String password, String firstname, String lastname, long role_id,
			int phoneNumber, String documentType, int documentNumber, String address, long zone_id, String turn) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role_id = role_id;
		this.phoneNumber = phoneNumber;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.zone_id = zone_id;
		this.turn = turn;
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

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(int role) {
		this.role_id = role;
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


	public long getZone_id() {
		return zone_id;
	}

	public void setZone_id(int zone_id) {
		this.zone_id = zone_id;
	}

	
}
