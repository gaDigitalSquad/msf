package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="state",schema="msf")
public class State implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4234944231538353416L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "state_id")
	private long stateId;
	
	@Column(name = "description")
	private String description;
	
	public State() {
		
	}

	public State(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getStateId() {
		return stateId;
	}
	
	
	

}
