package ar.com.academy.mfs.model;

import java.util.Date;

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
	private long zoneId;
	
	@Column( name = "zone_name" )
	private String zoneName;
	
	@Column
	private Date from_date;
	

	@Column
	private Date to_date;
	
	public Zone() {
	}


	public Zone(String zoneName, Date from_date, Date to_date) {
		super();
		this.zoneName = zoneName;
		this.from_date = from_date;
		this.to_date = to_date;
	}

	public String getZoneName() {
		return zoneName;
	}


	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}


	public long getZoneId() {
		return zoneId;
	}
	
	public Date getFrom_date() {
		return from_date;
	}


	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}


	public Date getTo_date() {
		return to_date;
	}


	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	
	
}
