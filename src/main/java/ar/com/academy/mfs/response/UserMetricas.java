package ar.com.academy.mfs.response;

import ar.com.academy.mfs.model.User;

public class UserMetricas {
		
	private User user;
	private Metricas metrics;
	
	public UserMetricas() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMetricas(User user, Metricas metricas) {
		super();
		this.user = user;
		this.metrics = metricas;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Metricas getMetricas() {
		return metrics;
	}

	public void setMetricas(Metricas metricas) {
		this.metrics = metricas;
	}
	
	
	
}
