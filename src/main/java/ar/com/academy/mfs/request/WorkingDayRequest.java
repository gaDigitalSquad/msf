package ar.com.academy.mfs.request;

import java.sql.Time;
import java.util.Date;


public class WorkingDayRequest {

	private int supervisor_id;
	private int user_id;
	private boolean isPresent;
	private Date workingDate;
	private Time from_hour;
	private Time to_hour;
	private int amountOfNewPartners;
	private int totalAmount;
	private String observations;
	private boolean completed;

	public int getSupervisor() {
		return supervisor_id;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor_id = supervisor;
	}


	public int getUser() {
		return user_id;
	}


	public void setUser(int user) {
		this.user_id = user;
	}


	public boolean isPresent() {
		return isPresent;
	}


	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}


	public Date getWorkingDate() {
		return workingDate;
	}


	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
	}


	public Time getFrom_hour() {
		return from_hour;
	}


	public void setFrom_hour(Time from_hour) {
		this.from_hour = from_hour;
	}


	public Time getTo_hour() {
		return to_hour;
	}


	public void setTo_hour(Time to_hour) {
		this.to_hour = to_hour;
	}

	public int getAmountOfNewPartners() {
		return amountOfNewPartners;
	}


	public void setAmountOfNewPartners(int amountOfNewPartners) {
		this.amountOfNewPartners = amountOfNewPartners;
	}


	public int getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
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
