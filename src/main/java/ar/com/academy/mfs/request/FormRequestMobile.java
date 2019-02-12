package ar.com.academy.mfs.request;

import java.sql.Date;

public class FormRequestMobile {

	private int completed_by_user_id;
	private String firstname;
	private String lastname;
	private int dni;
	private String mobile_number;
	private String email;
	private float monthly_amount_contribution;
	private Date form_date;
	
	public FormRequestMobile(int completed_by_user_id, String firstname, String lastname, int dni,
			String mobile_number, String email, float monthly_amount_contribution, Date form_date) {
		super();
		this.completed_by_user_id = completed_by_user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dni = dni;
		this.mobile_number = mobile_number;
		this.email = email;
		this.monthly_amount_contribution = monthly_amount_contribution;
		this.form_date = form_date;
	}
	
	public FormRequestMobile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getCompleted_by_user_id() {
		return completed_by_user_id;
	}

	public void setCompleted_by_user_id(int completed_by_user_id) {
		this.completed_by_user_id = completed_by_user_id;
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

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	
	
}
