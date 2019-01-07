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
@Table(name="target",schema="msf")
public class Target implements Serializable{

	private static final long serialVersionUID = -3084303829038597812L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "target_id")
	private long targetId;
	
	@Column(name = "partner_hour")
	private float partner_hour;
	
	@Column(name = "amount")
	private float amount;
	
	@Column(name = "from_date")
	private Date from_date;
	
	@Column(name = "to_date")
	private Date to_date;
	
	public Target() {
		
	}

	public Target(float partner_hour, float amount, Date from_date, Date to_date) {
		super();
		this.partner_hour = partner_hour;
		this.amount = amount;
		this.from_date = from_date;
		this.to_date = to_date;
	}

	public float getPartner_hour() {
		return partner_hour;
	}

	public void setPartner_hour(float partner_hour) {
		this.partner_hour = partner_hour;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
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

	public long getTargetId() {
		return targetId;
	}	
	
}
