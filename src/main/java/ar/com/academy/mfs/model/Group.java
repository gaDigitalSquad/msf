package ar.com.academy.mfs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="group",schema="msf")
public class Group implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 314596368818188482L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "group_id")
	private long group_id;
	
	@Column(name = "supervisor_id") //LIDER
	private long supervisor;
	
	@Column(name="supervised_id") //SENSIBILIZADOR
	private long supervised;
	
	@Column(name="zone_id")
	private long zone_id;
	
	@Column(name="from_date")
	private Date from_date;
	
	@Column(name="to_date")
	private Date to_date;

	public Group() {
		
	}
	
	public Group(long supervisor_id, long supervised_id, long zone_id, Date from_date, Date to_date) {
		super();
		this.supervisor = supervisor_id;
		this.supervised = supervised_id;
		this.zone_id = zone_id;
		this.from_date = from_date;
		this.to_date = to_date;
	}

	public long getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(long supervisor_id) {
		this.supervisor = supervisor_id;
	}

	public long getSupervised() {
		return supervised;
	}

	public void setSupervised(long supervised_id) {
		this.supervised = supervised_id;
	}

	public long getZone_id() {
		return zone_id;
	}

	public void setZone_id(long zone_id) {
		this.zone_id = zone_id;
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

	public long getGroup_id() {
		return group_id;
	}
	

}
