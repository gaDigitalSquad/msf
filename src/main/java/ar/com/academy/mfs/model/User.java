package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.com.academy.mfs.model.UserState;
import ar.com.academy.mfs.model.Role;

@Entity
@Table(name="user",schema="msf")
public class User implements Serializable {

	private static final long serialVersionUID = 4904280768051028345L;

	@Id
	@Column( name = "user_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long user_id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column (name = "firstname")
	private String firstname;
	
	@Column (name = "lastname")
	private String lastname;
	
	@Column (name = "role_id")
	private int role_id;
	
	@Column (name = "phone_number")
	private int phoneNumber;
	
	@Column (name = "document_type")
	private String documentType;
	
	@Column (name = "document_number")
	private int documentNumber;
	
	@Column (name = "active")
	private boolean active = true;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_state_id")
	@JsonIgnore
	private UserState userState;

	
	
	public long getUser_id() {
		return user_id;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public UserState getUserState() {
		return userState;
	}
	public void setUserState(UserState userState) {
		this.userState = userState;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Override
	public String toString() {
		return this.lastname+' '+this.firstname;
	}
	
	public User() {
		
	}
	
	
	
	
	public User(String username, String password, String firstname, String lastname, int role_id, int phoneNumber,
			String documentType, int documentNumber) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role_id = role_id;
		this.phoneNumber = phoneNumber;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
	}
//	public User(UserRequest ur) {
//		super();
//		this.username= ur.getUsername();
//		this.firstname =ur.getFirstname();
//		this.lastname = ur.getLastname();
//		this.role = ur.getRole();
//		this.phoneNumber = ur.getPhoneNumber();
//		this.documentType = ur.getDocumentType();
//		this.documentNumber = ur.getDocumentNumber();
//		this.active = true;
//		this.user_state_id = ur.getUser_state_id();
//	}

	
	
}
