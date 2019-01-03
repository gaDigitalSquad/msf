package ar.com.academy.mfs.response;

public class Metricas {

	private int cantidadSocios;
	private float monto;
	
	
	
	public Metricas(int cantidadSocios, float monto) {
		super();
		this.cantidadSocios = cantidadSocios;
		this.monto = monto;
	}
	public int getCantidadSocios() {
		return cantidadSocios;
	}
	public void setCantidadSocios(int cantidadSocios) {
		this.cantidadSocios = cantidadSocios;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	
	
}
