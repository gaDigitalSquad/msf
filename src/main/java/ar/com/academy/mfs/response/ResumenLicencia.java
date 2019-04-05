package ar.com.academy.mfs.response;

public class ResumenLicencia {
	
	private String year;
	private int    month;
	private int    quantity;
	 
		
	public String getAnio() {
		return year;
	}
	public void setAnio(String anio) {
		this.year = anio;
	}
	public int getCantidad() {
		return quantity;
	}
	public void setCantidad(int cantidad) {
		this.quantity = cantidad;
	}	
	public ResumenLicencia(String anio, int cantidad, int mes)
	{
		this.year 		= anio;
		this.quantity	= cantidad;
		this.month		= mes;
	}
	public int getMes() {
		return month;
	}
	public void setMes(int mes) {
		this.month = mes;
	}
}
