package ar.com.academy.mfs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_state",schema="msf")
public class UserState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183459274763635393L;

	
	@Id
	@Column( name = "user_state_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int userStateId;
	

	@Column (name="from_date")
	private Date fromDate;
	
	
	@Column (name="to_date")
	private Date toDate;
	
	@Column (name="state_id")
	private int stateId;
	
	public UserState() {
		
	}

	public UserState(Date from_date, Date to_date, int state_id) {
		super();
		this.fromDate = from_date;
		this.toDate = to_date;
		this.stateId = state_id;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date from_date) {
		this.fromDate = from_date;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date to_date) {
		this.toDate = to_date;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int state_id) {
		this.stateId = state_id;
	}

	public int getUserStateId() {
		return userStateId;
	}
	
}
