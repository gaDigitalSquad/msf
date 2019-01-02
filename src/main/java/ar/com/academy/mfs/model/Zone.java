package ar.com.academy.mfs.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="zone",schema="msf")
public class Zone {
	
	@Id
	@Column( name = "zone_id")
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long zoneId;
	
	@Column( name = "zone_name" )
	private String zoneName;
	
	@Column(name="target_id")
	private long targetId;
	
	public Zone() {
	}

	public Zone(String zoneName, long targetId) {
		super();
		this.zoneName = zoneName;
		this.targetId = targetId;
	}

	public String getZoneName() {
		return zoneName;
	}


	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}


	public long getZoneId() {
		return zoneId;
	}


	public long getTargetId() {
		return targetId;
	}


	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
	
	
	
	
	
}
