package ar.com.academy.mfs.response;

import java.util.List;

import ar.com.academy.mfs.model.Partner;
import ar.com.academy.mfs.model.WorkingDay;

public class WorkingDayResponse {
	
	private WorkingDay workingDay;
	private List<Partner> partners;
	
	public WorkingDayResponse(WorkingDay workingDay, List<Partner> partners) {
		super();
		this.workingDay = workingDay;
		this.partners = partners;
	}

	public WorkingDay getWorkingDay() {
		return workingDay;
	}

	public void setWorkingDay(WorkingDay workingDay) {
		this.workingDay = workingDay;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

}
