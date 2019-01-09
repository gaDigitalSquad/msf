package ar.com.academy.mfs.request;

public class FormRequestMobile {

	private String completedByUser;
	private String firstname;
	private String lastname;
	private int dni;
	private String mobileNumber;
	private String email;
	private float monthlyAmountContribution;
	
	public FormRequestMobile(String completedByUser, String firstname, String lastname, int dni,
			String mobileNumber, String email, float monthlyAmountContribution) {
		super();
		this.completedByUser = completedByUser;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dni = dni;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.monthlyAmountContribution = monthlyAmountContribution;
	}
	
	public FormRequestMobile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCompletedByUser() {
		return completedByUser;
	}
	public void setCompletedByUser(String completedByUser) {
		this.completedByUser = completedByUser;
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
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
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
	public float getMonthlyAmountContribution() {
		return monthlyAmountContribution;
	}
	public void setMonthlyAmountContribution(float monthlyAmountContribution) {
		this.monthlyAmountContribution = monthlyAmountContribution;
	}

	
}
