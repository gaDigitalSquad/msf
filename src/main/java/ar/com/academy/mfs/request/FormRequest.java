package ar.com.academy.mfs.request;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class FormRequest {
	
	private String completedByUser;
	private String zone;
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
	private String cuil_cuit;
	private int dni;
	private String telephoneNumber;
	private String mobileNumber;
	private String email;
	private boolean didYouKnowMsf;
	private String cardType;
	private String cardNumber;
	private Date cardExpirationDate;
	private String cbu;
	private float monthlyAmountContribution;
	private Date formDate;
	private String observations;	
	private boolean completed;
	//Fijarse si lo ponemos nosotros en false o alguna manera para saber si se hizo en la la app mobile o en el portal web
	public String getCompletedByUser() {
		return completedByUser;
	}

	public void setCompletedByUser(String completedByUser) {
		this.completedByUser = completedByUser;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
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

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
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

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	

}

