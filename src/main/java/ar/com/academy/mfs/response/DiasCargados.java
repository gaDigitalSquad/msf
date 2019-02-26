package ar.com.academy.mfs.response;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DiasCargados {
	
	private String title;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean allDay;
	
	public DiasCargados(String title, LocalDateTime localDateTime, LocalDateTime localDateTime2, boolean allDay) {
		super();
		this.title = title;
		this.startTime = localDateTime;
		this.endTime = localDateTime2;
		this.allDay = allDay;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

}
