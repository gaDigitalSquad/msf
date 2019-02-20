package ar.com.academy.mfs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="card_type",schema="msf")
public class CardType implements Serializable{
	
	private static final long serialVersionUID = -7235472775439078110L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "card_type_id")
	private int cardTypeId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "code")
	private String code;
	
	public CardType() {
		
	}

	public CardType(String description, String code) {
		super();
		this.description = description;
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCardTypeId() {
		return cardTypeId;
	}
	
}
