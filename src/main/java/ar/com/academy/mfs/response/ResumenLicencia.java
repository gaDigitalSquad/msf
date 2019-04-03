package ar.com.academy.mfs.response;

public class ResumenLicencia {
	
	private String anio;
	private int    cantidad;
	

	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	
	public ResumenLicencia(String anio, int cantidad)
	{
		this.anio 		= anio;
		this.cantidad	= cantidad;
	}
}
