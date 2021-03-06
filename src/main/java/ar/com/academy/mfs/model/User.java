package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.academy.mfs.request.UserRequest;

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
	
	/*//hacer join tables?
	private Login login;*/
	
	//@ManyToOne
	//@JoinTable(name = "role")
	@Column (name="role_id")
	private int role_id;
	
	@Column (name = "email")
	private String email;
	
	@Column (name = "phone_number")
	private int phoneNumber;
	
	@Column (name = "document_type")
	private String documentType;
	
	@Column (name = "document_number")
	private int documentNumber;
	
	@Column (name = "address")
	private String address;
	
	@Column (name = "active")
	private boolean active;

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
		return this.role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	/*public Login getLogin() {
		return login;
	}
	public void setLogin(Login login) {
		this.login = login;
	}*/
	
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
	public User(String username, String firstname, String lastname, int role,
			String email, int phoneNumber, String documentType, int documentNumber, String address) {
		super();
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role_id = role;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.address = address;
		this.active = true;
	}
	public User() {
		
	}
	
	public User(UserRequest ur) {
		super();
		this.username= ur.getUsername();
		this.firstname =ur.getFirstname();
		this.lastname = ur.getLastname();
		this.role_id = ur.getRole_id();
		this.email = ur.getEmail();
		this.phoneNumber = ur.getPhoneNumber();
		this.documentType = ur.getDocumentType();
		this.documentNumber = ur.getDocumentNumber();
		this.address = ur.getAddress();
		this.active = true;
	}

	
	
}
