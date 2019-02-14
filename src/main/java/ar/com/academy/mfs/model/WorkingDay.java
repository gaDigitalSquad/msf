package ar.com.academy.mfs.model;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "working_day", schema = "msf")
public class WorkingDay {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column( name = "working_day_id")
	private int workingDayId;
	
	@Column(name = "supervisor_id")
	private int supervisor;
	
	@Column(name = "user_id")
	private int user;
	
	@Column(name = "present")
	private boolean present;
	
	@Column(name = "working_date")
	private Date workingDate;
	
	@Column(name="from_hour")
	private Time from_hour;
	
	@Column(name="to_hour")
	private Time to_hour;
	
	@Column(name = "zone_id")
	private int zone;
	
	@Column(name = "amount_of_new_partners")
	private int amountOfNewPartners;
	
	@Column(name = "total_amount")
	private float totalAmount;
	
	@Column(name = "observations")
	private String observations;
	
	@Column(name = "completed")
	private boolean isCompleted;
	
	@Column(name = "hours_worked")
	private int hours_worked;
	
	@Column(name = "group_number")
	private int group_number;
	
	public WorkingDay() {
	}
	
	public WorkingDay(int supervisor, int user, boolean present, Date workingDate, Time from_hour, Time to_hour,
			int zone, int amountOfNewPartners, float totalAmount, String observations, int hours_worked, boolean isCompleted) {
		super();
		this.supervisor = supervisor;
		this.user = user;
		this.present = present;
		this.workingDate = workingDate;
		this.from_hour = from_hour;
		this.to_hour = to_hour;
		this.zone = zone;
		this.amountOfNewPartners = amountOfNewPartners;
		this.totalAmount = totalAmount;
		this.observations = observations;
		this.isCompleted = isCompleted;
		this.hours_worked = hours_worked;
	}
	
	public int getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public void setWorkingDayId(int workingDayId) {
		this.workingDayId = workingDayId;
	}

	public boolean isPresent() {
		return present;
	}
	public void setPresent(boolean present) {
		this.present = present;
	}
	public Date getWorkingDate() {
		return workingDate;
	}
	public void setWorkingDate(Date workingDate) {
		this.workingDate = workingDate;
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
	public float getWorkingDayId() {
		return workingDayId;
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
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public int getHours_worked() {
		return hours_worked;
	}

	public void setHours_worked(int hours_worked) {
		this.hours_worked = hours_worked;
	}

	public int getGroup_number() {
		return group_number;
	}

	public void setGroup_number(int group_number) {
		this.group_number = group_number;
	}
}
