package ar.com.academy.mfs.request;

import java.sql.Date;
import java.util.List;

public class GroupRequest {

	private int leader;
	private List<Integer> sens;
	private int zone;
	private int area;

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public int getLeader() {
		return leader;
	}

	public void setLeader(int leader) {
		this.leader = leader;
	}

	public List<Integer> getSens() {
		return sens;
	}

	public void setSens(List<Integer> sens) {
		this.sens = sens;
	}
	
}
