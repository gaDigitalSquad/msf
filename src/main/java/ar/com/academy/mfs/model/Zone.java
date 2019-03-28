package ar.com.academy.mfs.model;


import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zone",schema="msf")
public class Zone {
	
	@Id
	@Column( name = "zone_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int zoneId;
	
	@Column( name = "zone_name" )
	private String zoneName;
	
	@Column(name="target")
	private float target;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "hours")
	private float hours;
	
	@Column(name = "from_date")
	private Date fromDate;
	
	@Column(name = "to_date")
	private Date toDate;
	
	public Zone() {
	}

	public Zone(String zoneName, float target, float amount, float hours) {
		super();
		this.zoneName = zoneName;
		this.target = target;
		this.amount = amount;
		this.hours = hours;
		this.fromDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());;
	}
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public int getZoneId() {
		return zoneId;
	}
	public float getTarget() {
		return target;
	}
	public void setTargetId(float target) {
		this.target = target;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	public void setTarget(float target) {
		this.target = target;
	}
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
}
