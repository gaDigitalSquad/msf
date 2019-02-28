package ar.com.academy.mfs.request;

import java.sql.Date;

public class UserStateRequest {
	
	private int description;
	private int licenseTo;
	private Date fromDate;
	private Date toDate;
	
	public int getDescription() {
		return description;
	}
	public void setDescription(int description) {
		this.description = description;
	}
	public int getLicenseTo() {
		return licenseTo;
	}
	public void setLicenseTo(int licenseTo) {
		this.licenseTo = licenseTo;
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

}
