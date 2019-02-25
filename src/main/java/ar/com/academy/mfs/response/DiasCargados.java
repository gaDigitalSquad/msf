package ar.com.academy.mfs.response;

import java.sql.Timestamp;

public class DiasCargados {
	
	private String title;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean allDay;
	
	public DiasCargados(String title, Timestamp timestamp, Timestamp timestamp2, boolean allDay) {
		super();
		this.title = title;
		this.startTime = timestamp;
		this.endTime = timestamp2;
		this.allDay = allDay;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

}
