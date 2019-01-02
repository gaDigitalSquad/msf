package ar.com.academy.mfs.request;

import java.sql.Date;
import java.sql.Time;

public class WorkingDayRequest {


	private long supervisor;
	private long user;
	private boolean isPresent;
	private Date workingDate;
	private Time from_hour;
	private Time to_hour;
	private int zone;
	private int amountOfNewPartners;
	private float totalAmount;
	private String observations;
	private boolean completed;
	
	
	public WorkingDayRequest() {}


	public long getSupervisor() {
		return supervisor;
	}


	public void setSupervisor(long supervisor) {
		this.supervisor = supervisor;
	}


	public long getUser() {
		return user;
	}


	public void setUser(long user) {
		this.user = user;
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


	public int getZone() {
		return zone;
	}


	public void setZone(int zone) {
		this.zone = zone;
	}


	public int getAmountOfNewPartners() {
		return amountOfNewPartners;
	}


	public void setAmountOfNewPartners(int amountOfNewPartners) {
		this.amountOfNewPartners = amountOfNewPartners;
	}


	public float getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(float totalAmount) {
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
