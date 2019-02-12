package ar.com.academy.mfs.response;

import java.sql.Time;

public class DiasCargados {
	
	private String title;
	private Time startTime;
	private Time endTime;
	private boolean allDay;
	
	public DiasCargados(String title, Time startTime, Time endTime, boolean allDay) {
		super();
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.allDay = allDay;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Time getStartTime() {
		return startTime;
	}
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	public Time getEndTime() {
		return endTime;
	}
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

}
