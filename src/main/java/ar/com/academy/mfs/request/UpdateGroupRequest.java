package ar.com.academy.mfs.request;

import java.util.List;

import ar.com.academy.mfs.model.User;

public class UpdateGroupRequest {
	
	private int leader;
	private int newLeader;
	private List<User> sens;
	private List<User> sensWithoutGroup;
	
	public int getLeader() {
		return leader;
	}
	public void setLeader(int leader) {
		this.leader = leader;
	}
	public int getNewLeader() {
		return newLeader;
	}
	public void setNewLeader(int newLeader) {
		this.newLeader = newLeader;
	}
	public List<User> getSens() {
		return sens;
	}
	public void setSens(List<User> sens) {
		this.sens = sens;
	}
	public List<User> getSensWithoutGroup() {
		return sensWithoutGroup;
	}
	public void setSensWithoutGroup(List<User> sensWithoutGroup) {
		this.sensWithoutGroup = sensWithoutGroup;
	}
	
}
