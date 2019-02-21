package ar.com.academy.mfs.request;

import java.util.List;

public class GroupRequest {

	private int leader;
	private List<Integer> sens; // Lista con los id de los sensibilizadores
	private int zone;
	private String turn;
	private int groupNumber;
	
	public int getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getturn() {
		return turn;
	}

	public void setturn(String turn) {
		this.turn = turn;
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
