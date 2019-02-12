package ar.com.academy.mfs.response;

public class Metricas {

	private float amount_of_new_partners;
	private float amount;
	private float hours_worked;
	private float partner_hours;
	private float averageAmount;
	private int sens_id;
	
	public Metricas(float amount_of_new_partners, float amount, float hours_worked, float partner_hours) {
		super();
		this.amount_of_new_partners = amount_of_new_partners;
		this.amount = amount;
		this.hours_worked = hours_worked;
		this.partner_hours = partner_hours;
		this.averageAmount = amount/hours_worked;
	}
	
	public float getamount_of_new_partners() {
		return amount_of_new_partners;
	}
	public void setamount_of_new_partners(float amount_of_new_partners) {
		this.amount_of_new_partners = amount_of_new_partners;
	}
	public float getamount() {
		return amount;
	}
	public void setamount(float amount) {
		this.amount = amount;
	}
	public float gethours_worked() {
		return hours_worked;
	}
	public void sethours_worked(float hours_worked) {
		this.hours_worked = hours_worked;
	}
	public float getpartner_hours() {
		return partner_hours;
	}
	public void setpartner_hours(float partner_hours) {
		this.partner_hours = partner_hours;
	}
	public float getaverageAmount() {
		return averageAmount;
	}
	public void setaverageAmount(float averageAmount) {
		this.averageAmount = averageAmount;
	}
	public int getSens_id() {
		return sens_id;
	}
	public void setSens_id(int sens_id) {
		this.sens_id = sens_id;
	}
	
}
