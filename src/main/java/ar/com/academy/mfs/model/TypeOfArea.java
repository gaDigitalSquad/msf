package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="type_of_area",schema="msf")
public class TypeOfArea implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4857142900748296279L;
	

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "type_of_area_id")
	private long typeOfAreaId;
	

	@Column(name = "description")
	private String description;
	
	TypeOfArea(){
		
	}

	public TypeOfArea(String description) {
		super();
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTypeOfAreaId() {
		return typeOfAreaId;
	}
	
	

}
