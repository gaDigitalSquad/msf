package ar.com.academy.mfs.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role",schema="msf")
public class Role implements Serializable{

	private static final long serialVersionUID = -3018106058145791442L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private long role_id;
	
	@Column(name = "role_name")
	private String nameRole;
	
	
	public long getRole_id() {
		return role_id;
	}
	public String getNameRole() {
		return nameRole;
	}
	public void setNameRole(String nameRole) {
		this.nameRole = nameRole;
	}
	
	/*private Set<String> action_grants;


	public Set<String> getAction_grants() {
		return action_grants;
	}
	public void setAction_grants(Set<String> action_grants) {
		this.action_grants = action_grants;
	} */
	
	
	
	
}
