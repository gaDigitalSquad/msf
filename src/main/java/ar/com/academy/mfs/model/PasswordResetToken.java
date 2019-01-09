package ar.com.academy.mfs.model;

import java.util.Date;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="password_reset",schema="msf")
public class PasswordResetToken {
  
	private static final int EXPIRATION = 60 * 24;
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "password_reset_id")
    private long password_reset_id;
    
    private String token;
  
    @Column(name = "user_id")
    private int user_id;
  
    private Date expiryDate;
    
    public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PasswordResetToken(String token, int user_id) {
 		this.token=token;
 		this.user_id=user_id;
 		this.expiryDate = calculateExpiryDate(EXPIRATION);
 	}

	public long getPassword_reset_id() {
		return password_reset_id;
	}

	public void setPassword_reset_id(long password_reset_id) {
		this.password_reset_id = password_reset_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getUser_id() {
		return user_id;
	}
      
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    
}