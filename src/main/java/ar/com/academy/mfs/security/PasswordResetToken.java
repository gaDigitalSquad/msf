package ar.com.academy.mfs.security;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ar.com.academy.mfs.model.User;

@Entity
@Table(name="password_reset",schema="msf")
public class PasswordResetToken {
  
	private static final int EXPIRATION = 60 * 24;
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "password_reset_id")
    private Long password_reset_id;
  
    private String token;
  
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
  
    private Date expiryDate;
    
    public PasswordResetToken(String token, User user) {
 		this.token=token;
 		this.user=user;
 	}

}