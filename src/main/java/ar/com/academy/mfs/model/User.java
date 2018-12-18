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
	private long role_id;
	
	@Column (name="zone_id")
	private long zone_id;
	
	@Column (name = "phone_number")
	private int phoneNumber;
	
	@Column (name = "document_type")
	private String documentType;
	
	@Column (name = "document_number")
	private int documentNumber;
	
	@Column (name = "active")
	private boolean active;
	
	@Column (name="turn")
	private String turn;
	
	@Column (name="user_state_id")
	private long user_state_id;

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
	public long getRole_id() {
		return this.role_id;
	}
	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	
	public long getZone_id() {
		return zone_id;
	}
	
	public void setZone_id(int zone_id) {
		this.zone_id = zone_id;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public long getUser_state_id() {
		return user_state_id;
	}
	public void setUser_state_id(long user_state_id) {
		this.user_state_id = user_state_id;
	}
	public String getTurn() {
		return turn;
	}
	public void setTurn(String turn) {
		this.turn = turn;
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
	
	public User() {
		
	}
	
	
	
	
	public User(String username, String password, String firstname, String lastname, int role_id, int zone_id,
			int phoneNumber, String documentType, int documentNumber, boolean active, String turn, long user_state_id) {
		super();
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role_id = role_id;
		this.zone_id = zone_id;
		this.phoneNumber = phoneNumber;
		this.documentType = documentType;
		this.documentNumber = documentNumber;
		this.active = active;
		this.turn = turn;
		this.user_state_id = user_state_id;
	}
	public User(UserRequest ur) {
		super();
		this.username= ur.getUsername();
		this.firstname =ur.getFirstname();
		this.lastname = ur.getLastname();
		this.role_id = ur.getRole_id();
		this.phoneNumber = ur.getPhoneNumber();
		this.documentType = ur.getDocumentType();
		this.documentNumber = ur.getDocumentNumber();
		this.active = true;
		this.zone_id = ur.getZone_id();
		this.user_state_id = ur.getUser_state_id();
	}

	
	
}
