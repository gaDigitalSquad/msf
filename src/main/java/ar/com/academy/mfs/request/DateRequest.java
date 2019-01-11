package ar.com.academy.mfs.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class DateRequest {
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date from;
	private Date to;

	
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	
	
}
