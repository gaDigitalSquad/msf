package ar.com.academy.mfs.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity; //Guarda el dia sin la hora exacta para poder matchearlo con SQL
import javax.persistence.Id;


public class Form implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1179958879981262825L;
	

	
	private long formId;
	private User completedByUser;
	private Zone zone;
	private String memberType;
	private String firstname;
	private String lastname;
	private Date birthdate;
	private String addressStreet;
	private int addressNumber;
	private int addressFloor;
	private String addressApartment;
	private String postcode;
	private String city;
	private String province;
	private String cuilCuit;
	private int dni;
	private String telNumber;
	private String mobileNumber;
	private String email;
	private boolean didYouKnowMsf;
	private String cardType;
	private String cardNumber;
	private Date cardExpirationDate;
	private String cbu;
	private float monthlyAmountContribution;
	private float increaseAmount;
	private float finalAmount;
	private Date formDate;
	private String observations;
	
	
	public Form() {	}


	public Form(User completedByUser, Zone zone, String memberType, String firstname, String lastname, Date birthdate,
			String addressStreet, int addressNumber, int addressFloor, String addressApartment, String postcode,
			String city, String province, String cuilCuit, int dni, String telNumber, String mobileNumber, String email,
			boolean didYouKnowMsf, String cardType, String cardNumber, Date cardExpirationDate, String cbu,
			float monthlyAmountContribution, float increaseAmount, float finalAmount, Date formDate,
			String observations) {
		super();
		this.completedByUser = completedByUser;
		this.zone = zone;
		this.memberType = memberType;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.addressStreet = addressStreet;
		this.addressNumber = addressNumber;
		this.addressFloor = addressFloor;
		this.addressApartment = addressApartment;
		this.postcode = postcode;
		this.city = city;
		this.province = province;
		this.cuilCuit = cuilCuit;
		this.dni = dni;
		this.telNumber = telNumber;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.didYouKnowMsf = didYouKnowMsf;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.cardExpirationDate = cardExpirationDate;
		this.cbu = cbu;
		this.monthlyAmountContribution = monthlyAmountContribution;
		this.increaseAmount = increaseAmount;
		this.finalAmount = finalAmount;
		this.formDate = formDate;
		this.observations = observations;
	}


	public User getCompletedByUser() {
		return completedByUser;
	}


	public void setCompletedByUser(User completedByUser) {
		this.completedByUser = completedByUser;
	}


	public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}


	public String getMemberType() {
		return memberType;
	}


	public void setMemberType(String memberType) {
		this.memberType = memberType;
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


	public String getAddressStreet() {
		return addressStreet;
	}


	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}


	public int getAddressNumber() {
		return addressNumber;
	}


	public void setAddressNumber(int addressNumber) {
		this.addressNumber = addressNumber;
	}


	public int getAddressFloor() {
		return addressFloor;
	}


	public void setAddressFloor(int addressFloor) {
		this.addressFloor = addressFloor;
	}


	public String getAddressApartment() {
		return addressApartment;
	}


	public void setAddressApartment(String addressApartment) {
		this.addressApartment = addressApartment;
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


	public String getCuilCuit() {
		return cuilCuit;
	}


	public void setCuilCuit(String cuilCuit) {
		this.cuilCuit = cuilCuit;
	}


	public int getDni() {
		return dni;
	}


	public void setDni(int dni) {
		this.dni = dni;
	}


	public String getTelNumber() {
		return telNumber;
	}


	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public boolean isDidYouKnowMsf() {
		return didYouKnowMsf;
	}


	public void setDidYouKnowMsf(boolean didYouKnowMsf) {
		this.didYouKnowMsf = didYouKnowMsf;
	}


	public String getCardType() {
		return cardType;
	}


	public void setCardType(String cardType) {
		this.cardType = cardType;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public Date getCardExpirationDate() {
		return cardExpirationDate;
	}


	public void setCardExpirationDate(Date cardExpirationDate) {
		this.cardExpirationDate = cardExpirationDate;
	}


	public String getCbu() {
		return cbu;
	}


	public void setCbu(String cbu) {
		this.cbu = cbu;
	}


	public float getMonthlyAmountContribution() {
		return monthlyAmountContribution;
	}


	public void setMonthlyAmountContribution(float monthlyAmountContribution) {
		this.monthlyAmountContribution = monthlyAmountContribution;
	}


	public float getIncreaseAmount() {
		return increaseAmount;
	}


	public void setIncreaseAmount(float increaseAmount) {
		this.increaseAmount = increaseAmount;
	}


	public float getFinalAmount() {
		return finalAmount;
	}


	public void setFinalAmount(float finalAmount) {
		this.finalAmount = finalAmount;
	}


	public Date getFormDate() {
		return formDate;
	}


	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}


	public String getObservations() {
		return observations;
	}


	public void setObservations(String observations) {
		this.observations = observations;
	}


	public long getFormId() {
		return formId;
	}
	
	
	
	
}
