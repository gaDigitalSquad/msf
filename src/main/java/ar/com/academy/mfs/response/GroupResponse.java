package ar.com.academy.mfs.response;

public class GroupResponse {
	
	private String zona;
	private int numero;
	private String turno;
	private String lider;
	
	public GroupResponse(String zona, int numero, String turno, String lider) {
		super();
		this.zona = zona;
		this.numero = numero;
		this.turno = turno;
		this.lider = lider;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getLider() {
		return lider;
	}
	public void setLider(String lider) {
		this.lider = lider;
	}
	
	@Override
	public int hashCode() {
	    return (int) zona.hashCode() * numero * turno.hashCode() * lider.hashCode();
	}

}
