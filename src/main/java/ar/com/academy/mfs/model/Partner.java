package ar.com.academy.mfs.model;

public class Partner {
	
	private int dni;
	private String firstname;
	private String lastname;
	private float monthly_amount_contribution;
	
	public Partner(int dni, String firstname, String lastname, float monthly_amount_contribution) {
		super();
		this.dni = dni;
		this.firstname = firstname;
		this.lastname = lastname;
		this.monthly_amount_contribution = monthly_amount_contribution;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
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

	public float getMonthly_amount_contribution() {
		return monthly_amount_contribution;
	}

	public void setMonthly_amount_contribution(float monthly_amount_contribution) {
		this.monthly_amount_contribution = monthly_amount_contribution;
	}	

}
