package ar.com.academy.mfs.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity; //Guarda el dia sin la hora exacta para poder matchearlo con SQL
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="form",schema="msf")
public class Form implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179958879981262825L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "form_id")
	private long form_id;
	
	@Column(name = "completed_by_user_id")
	private long completed_by_user_id;
	
	
	@Column(name = "zone_id")
	private long zone_id;

	@Column(name = "member_type")
	private String member_type;
	
	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column (name="birthdate")
	private Date birthdate;
	
	@Column(name = "address_street")
	private String address_street;
	
	@Column(name = "address_number")
	private int address_number;
	
	@Column(name = "address_floor")
	private int address_floor;
	
	@Column(name = "address_apartment")
	private String address_apartment;
	
	@Column(name = "postcode")
	private String postcode;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "cuil_cuit")
	private String cuil_cuit;
	
	@Column(name = "dni")
	private int dni;

	@Column(name = "phone_number")
	private String phone_number;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "did_you_know_msf")
	private boolean did_you_know_msf;
	
	@Column(name = "card_type_id")
	private long card_type_id;
	
	@Column(name = "card_number")
	private String card_number;
	
	@Column(name = "card_expiration_date")
	private Date card_expiration_date;
	
	@Column(name = "cbu")
	private String cbu;
	
	@Column(name = "monthly_amount_contribution")
	private float monthly_amount_contribution;
	
	@Column(name = "form_date")
	private Date form_date;
	
	@Column(name = "observations")
	private String observations;
	
	@Column(name = "completed")
	private boolean completed;
	
	public Form() {	}

	
	
	public Form(long completed_by_user_id, long zone_id, String member_type, String firstname, String lastname,
			Date birthdate, String address_street, int address_number, int address_floor, String address_apartment,
			String postcode, String city, String province, String cuil_cuit, int dni, String phone_number,
			String email, boolean did_you_know_msf, long card_type_id, String card_number,
			Date card_expiration_date, String cbu, float monthly_amount_contribution, Date form_date,
			String observations, boolean completed) {
		super();
		this.completed_by_user_id = completed_by_user_id;
		this.zone_id = zone_id;
		this.member_type = member_type;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.address_street = address_street;
		this.address_number = address_number;
		this.address_floor = address_floor;
		this.address_apartment = address_apartment;
		this.postcode = postcode;
		this.city = city;
		this.province = province;
		this.cuil_cuit = cuil_cuit;
		this.dni = dni;
		this.phone_number = phone_number;
		this.email = email;
		this.did_you_know_msf = did_you_know_msf;
		this.card_type_id = card_type_id;
		this.card_number = card_number;
		this.card_expiration_date = card_expiration_date;
		this.cbu = cbu;
		this.monthly_amount_contribution = monthly_amount_contribution;
		this.form_date = form_date;
		this.observations = observations;
		this.completed = completed;
	}

	public long getCompleted_by_user_id() {
		return completed_by_user_id;
	}

	public void setCompleted_by_user_id(long completed_by_user_id) {
		this.completed_by_user_id = completed_by_user_id;
	}

	public long getZone_id() {
		return zone_id;
	}

	public void setZone_id(long zone_id) {
		this.zone_id = zone_id;
	}

	public String getMember_type() {
		return member_type;
	}

	public void setMember_type(String member_type) {
		this.member_type = member_type;
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

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
	}

	public int getAddress_number() {
		return address_number;
	}

	public void setAddress_number(int address_number) {
		this.address_number = address_number;
	}

	public int getAddress_floor() {
		return address_floor;
	}

	public void setAddress_floor(int address_floor) {
		this.address_floor = address_floor;
	}

	public String getAddress_apartment() {
		return address_apartment;
	}

	public void setAddress_apartment(String address_apartment) {
		this.address_apartment = address_apartment;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCuil_cuit() {
		return cuil_cuit;
	}

	public void setCuil_cuit(String cuil_cuit) {
		this.cuil_cuit = cuil_cuit;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getphone_number() {
		return phone_number;
	}

	public void setphone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isDid_you_know_msf() {
		return did_you_know_msf;
	}

	public void setDid_you_know_msf(boolean did_you_know_msf) {
		this.did_you_know_msf = did_you_know_msf;
	}

	public long getCard_type_id() {
		return card_type_id;
	}

	public void setCard_type_id(long card_type_id) {
		this.card_type_id = card_type_id;
	}

	public String getCard_number() {
		return card_number;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public Date getCard_expiration_date() {
		return card_expiration_date;
	}

	public void setCard_expiration_date(Date card_expiration_date) {
		this.card_expiration_date = card_expiration_date;
	}

	public String getCbu() {
		return cbu;
	}

	public void setCbu(String cbu) {
		this.cbu = cbu;
	}

	public float getMonthly_amount_contribution() {
		return monthly_amount_contribution;
	}

	public void setMonthly_amount_contribution(float monthly_amount_contribution) {
		this.monthly_amount_contribution = monthly_amount_contribution;
	}

	public Date getForm_date() {
		return form_date;
	}

	public void setForm_date(Date form_date) {
		this.form_date = form_date;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public long getForm_id() {
		return form_id;
	}
}
