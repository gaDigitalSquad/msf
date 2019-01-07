package ar.com.academy.mfs.response;

public class Metricas {

	private float cantidadSocios;
	private float monto;
	private float horasTrabajadas;
	private float sociosHora;
	
	public Metricas(float cantidadSocios, float monto, float horasTrabajadas, float sociosHora) {
		super();
		this.cantidadSocios = cantidadSocios;
		this.monto = monto;
		this.horasTrabajadas = horasTrabajadas;
		this.sociosHora = sociosHora;
	}
	public float getCantidadSocios() {
		return cantidadSocios;
	}
	public void setCantidadSocios(float cantidadSocios) {
		this.cantidadSocios = cantidadSocios;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public float getHorasTrabajadas() {
		return horasTrabajadas;
	}
	public void setHorasTrabajadas(float horasTrabajadas) {
		this.horasTrabajadas = horasTrabajadas;
	}
	public float getSociosHora() {
		return sociosHora;
	}
	public void setSociosHora(float sociosHora) {
		this.sociosHora = sociosHora;
	}
	
}
