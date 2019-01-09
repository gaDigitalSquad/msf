package ar.com.academy.mfs.response;

import ar.com.academy.mfs.model.User;

public class UserMetricas {
		
	private User user;
	private Metricas metricas;
	
	public UserMetricas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMetricas(User user, Metricas metricas) {
		super();
		this.user = user;
		this.metricas = metricas;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Metricas getMetricas() {
		return metricas;
	}

	public void setMetricas(Metricas metricas) {
		this.metricas = metricas;
	}
	
	
	
}
