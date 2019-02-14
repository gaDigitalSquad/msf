package ar.com.academy.mfs.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.Form;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.repository.CardTypeRepository;
import ar.com.academy.mfs.repository.FormRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.FormRequest;
import ar.com.academy.mfs.request.FormRequestMobile;

@Service("formService")
public class FormService {
	
	@Autowired
	private FormRepository formRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ZoneRepository zoneRepository;
	
	@Autowired
	private CardTypeRepository cardTypeRepository;
	
//	public Form createForm(FormRequest inputForm, long user, long zone, long cardType) {
//		
//		Form formToSave = new Form(user, 
//				zone, 
//				inputForm.getMemberType(), 
//				inputForm.getFirstname(), 
//				inputForm.getLastname(), 
//				inputForm.getBirthdate(), 
//				inputForm.getAddressStreet(), 
//				inputForm.getAddressNumber(), 
//				inputForm.getAddressFloor(), 
//				inputForm.getAddressApartment(), 
//				inputForm.getPostcode(), 
//				inputForm.getCity(), 
//				inputForm.getProvince(), 
//				inputForm.getCuil_cuit(), 
//				inputForm.getDni(), 
//				inputForm.getTelephoneNumber(), 
//				inputForm.getMobileNumber(), 
//				inputForm.getEmail(), 
//				inputForm.isDidYouKnowMsf(), 
//				cardType, 
//				inputForm.getCardNumber(), 
//				inputForm.getCardExpirationDate(), 
//				inputForm.getCbu(), 
//				inputForm.getMonthlyAmountContribution(), 
//				inputForm.getFormDate(), 
//				inputForm.getObservations(), 
//				inputForm.isCompleted());
//		
//		Form formSaved = formRepository.save(formToSave);
//		return formSaved;
//	}

	public Form getFormByDni(int dni) {
		Optional<Form> form = formRepository.findByDni(dni);
		return form.get();
	}

	public Form createForm(FormRequestMobile inputForm) {
		User user = userRepository.findById(inputForm.getCompleted_by_user_id()).get();
		Form formToSave = new Form(inputForm.getCompleted_by_user_id(), 
				inputForm.getFirstname(), 
				inputForm.getLastname(), 
				inputForm.getDni(), 
				inputForm.getEmail(),
				inputForm.getMobile_number(),
				inputForm.getMonthly_amount_contribution(),
				user.getZone_id(),
				inputForm.getForm_date()
				);
		Form formSaved = formRepository.save(formToSave);
		return formSaved;
	}

	public List<Form> getFormsByUser(int user, Date date) {
		return formRepository.findByUserAndDate(user, date);
	}

//	public Form updateFormDni(int dni, Form form) {
//		Optional<Form> formToUpdate = formRepository.findByDni(dni);
//		Form formActual = formToUpdate.get();
//		if(form.getFirstname() != null)
//			formActual.setFirstname(form.getFirstname());
//		if(form.getLastname() != null)
//			formActual.setLastname(form.getLastname());
//		if(form.getAddress_street() != null)
//			formActual.setAddress_street(form.getAddress_street());
//		if(form.getAddress_number() != 0)
//			formActual.setAddress_number(form.getAddress_number());
//		if(form.getAddress_floor() != 0)
//			formActual.setAddress_floor(form.getAddress_floor());
//		if(form.getAddress_apartment() != null)
//			formActual.setAddress_apartment(form.getAddress_apartment());
//		if(form.getPostcode() != null)
//			formActual.setPostcode(form.getPostcode());
//		if(form.getCity() != null)
//			formActual.setCity(form.getCity());
//		if(form.getProvince() != null)
//			formActual.setProvince(form.getProvince());
//		if(form.getTelephone_number() != null)
//			formActual.setTelephone_number(form.getTelephone_number());
//		if(form.getMobile_number() != null)
//			formActual.setMobile_number(form.getMobile_number());
//		if(form.getEmail() != null)
//			formActual.setEmail(form.getEmail());
//		if(form.getCard_type_id() != 0)
//			formActual.setCard_type_id(form.getCard_type_id());
//		if(form.getCard_number() != null)
//			formActual.setCard_number(form.getCard_number());
//		if(form.getCard_expiration_date()!=null)
//			formActual.setCard_expiration_date(form.getCard_expiration_date());
//		Form formSaved = formRepository.save(formActual);
//		return formSaved;
//	}

//	public Form createForm(FormRequestMobile inputForm, int user_id) {
//		Form formToSave = new Form(user_id, 
//				inputForm.getFirstname(), 
//				inputForm.getLastname(), 
//				inputForm.getDni(), 
//				inputForm.getEmail(), 
//				inputForm.getMonthlyAmountContribution());
//		Form formSaved = formRepository.save(formToSave);
//		return formSaved;
//	}
	
}
