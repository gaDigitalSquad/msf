package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="action",schema="msf")
public class Action implements Serializable {
	private static final long serialVersionUID = -3710684712314889614L;
	@Id
	@Column( name = "action_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int action_id;
	
	@Column(name = "description")
	private String description;
}
