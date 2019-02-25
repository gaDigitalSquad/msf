package ar.com.academy.mfs.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Form;
import ar.com.academy.mfs.model.Group;
import ar.com.academy.mfs.model.Partner;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.WorkingDay;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.WorkingDayRepository;
import ar.com.academy.mfs.request.DateRequest;
import ar.com.academy.mfs.request.WorkingDayRequest;
import ar.com.academy.mfs.response.Metricas;
import ar.com.academy.mfs.response.WorkingDayResponse;

@Service("workingDayService")
public class WorkingDayService {

	@Autowired
	WorkingDayRepository workingDayRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	FormService formService;
	@Autowired
	GroupService groupService;
	
	public Metricas getMetricasUsuario(int user_id, DateRequest dateRequest) {
		
		User user = userRepository.findById(user_id).get();
		
		List<WorkingDay> workingDays = workingDayRepository.findByWorkingDateBetweenAndUser(user.getUser_id(), dateRequest.getFrom(), dateRequest.getTo());
		
		float socios = 0;
		float monto = 0;
		float horas = 0;
		float sociosHora = 0;
		
		for(WorkingDay wk: workingDays) {
			socios = socios + wk.getAmountOfNewPartners();
			monto = monto + wk.getTotalAmount();
			horas = horas + wk.getHours_worked();
		}
		
		sociosHora = socios / horas;

		Metricas m = new Metricas(socios, monto, horas, sociosHora);
		
		return m;
	}
	
	public WorkingDay getWorkingDay(int user_id, DateRequest date){
		   return workingDayRepository.findByWorkingDateAndUser(user_id, date.getTo());
		}

	public WorkingDayResponse createWorkingDayResponse(WorkingDayRequest inputWorkingDay) {
		
		User lider = userRepository.findById(inputWorkingDay.getSupervisor()).get();
		int zone = lider.getZone_id();
		
		int hoursWorked = 0;
		
		/* Cálculo de horas trabajadas, solo si las horas son != null */
		if (inputWorkingDay.getTo_hour() != null && inputWorkingDay.getFrom_hour() != null) {
			DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
			long diff = inputWorkingDay.getTo_hour().getTime() - inputWorkingDay.getFrom_hour().getTime();
			hoursWorked = (int) (diff / (60 * 60 * 1000));
		}
		
		Group group = groupService.findGroup(inputWorkingDay.getSupervisor());
		WorkingDay WorkingDayToSave = new WorkingDay(lider.getUser_id(),
										inputWorkingDay.getUser(),
										inputWorkingDay.isIs_present(),
										inputWorkingDay.getWorkingDate(),
										inputWorkingDay.getFrom_hour(),
										inputWorkingDay.getTo_hour(),
										zone,
										inputWorkingDay.getAmountOfNewPartners(),
										inputWorkingDay.getTotalAmount(),
										inputWorkingDay.getObservations(),
										hoursWorked,
										inputWorkingDay.isCompleted(),
										group.getGroup_number());
		WorkingDay workingDaySaved = workingDayRepository.save(WorkingDayToSave);
		
		// Obtengo los formularios que hizo el sensibilizador en la fecha
		List<Form> formularios = formService.getFormsByUser(inputWorkingDay.getUser(), inputWorkingDay.getWorkingDate()); 
		
		// Establezco la lista de socios que hizo en la fecha
		List<Partner> partners = new ArrayList<Partner>();
		
		// De cada formulario, obtengo la información básica de cada socio
		for (Form formulario: formularios) {
			Partner socio = new Partner(formulario.getDni(), formulario.getFirstname(), formulario.getLastname(), formulario.getMonthly_amount_contribution());
			partners.add(socio);
		}
		
		return new WorkingDayResponse(workingDaySaved, partners);
	}

	public boolean existWorkingDay(int sens_id, Date fecha) {
		if (workingDayRepository.findByWorkingDateAndUser(sens_id, fecha) != null) {
			return true;
		}
		return false;
	}

}
