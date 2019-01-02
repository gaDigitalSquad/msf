package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="area",schema="msf")
public class Area implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4201632408559267335L;
	
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "area_id")
	private long area_id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "type_of_area_id")
	private long typeOfAreaId;
	
	@Column(name = "zone_id")
	private long zoneId;

	public Area() {
		
	}
	
	public Area(String description, long type_of_area_id, long zone_id) {
		super();
		this.description = description;
		this.typeOfAreaId = type_of_area_id;
		this.zoneId = zone_id;
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

	public void setTypeOfAreaId(long type_of_area_id) {
		this.typeOfAreaId = type_of_area_id;
	}

	public long getZoneId() {
		return zoneId;
	}

	public void setZoneId(long zone_id) {
		this.zoneId = zone_id;
	}

	public long getArea_id() {
		return area_id;
	}
	
	
	
	

}
