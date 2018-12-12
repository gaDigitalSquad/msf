package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="action_grant",schema="msf")
public class ActionGrant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5468912595004195325L;
	@Id
	@Column( name = "action_grant_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int action_grant_id;
	
	@Column( name = "action_id")
	private int action_id;

	@Column( name = "role_id")
	private int role_id;
	
	

	@ManyToOne(cascade = CascadeType.ALL)
	public int getRole_id() {
		return this.role_id;
	}
	@ManyToOne(cascade = CascadeType.ALL)
	public int getAction_id() {
		return this.action_id;	
	}
	
	public void setRole_id(int role_id){
		this.role_id = role_id ;
	}
	
	public void setAction(int action_id) {
		this.action_id = action_id;
	}

	
	
	
	
	
}
