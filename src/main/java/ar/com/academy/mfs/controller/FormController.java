package ar.com.academy.mfs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.CardType;
import ar.com.academy.mfs.model.Form;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.CardTypeRepository;
import ar.com.academy.mfs.repository.FormRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
import ar.com.academy.mfs.request.DateRequest;
import ar.com.academy.mfs.request.FormRequest;
import ar.com.academy.mfs.request.FormRequestMobile;
import ar.com.academy.mfs.service.FormService;

@RestController
public class FormController {
	@Autowired
	private FormService formService;

	@Autowired
	private FormRepository formRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ZoneRepository zoneRepository;

	@Autowired
	private CardTypeRepository cardTypeRepository;

	/**
	 * Completar un formulario proveniente de la app mobile
	 * 
	 * @param form
	 * @return
	 */

	@PostMapping("/form-2")
	public ResponseEntity<?> saveForm(@RequestBody FormRequest form) {
		/*
		 * Primero, encontramos el formulario a actualizar, lo buscamos por dni del
		 * socio ya que este no se puede repetir
		 */
		Form formToUpdate = formService.getFormByDni(form.getDni());
		if (formToUpdate != null) {
			formToUpdate.setFirstname(form.getFirstname());
			formToUpdate.setLastname(form.getLastname());
			formToUpdate.setBirthdate(form.getBirthdate());
			formToUpdate.setAddress_apartment(form.getAddressApartment());
			formToUpdate.setAddress_floor(form.getAddressFloor());
			formToUpdate.setAddress_number(form.getAddressNumber());
			formToUpdate.setAddress_street(form.getAddressStreet());
			formToUpdate.setCard_expiration_date(form.getCardExpirationDate());
			formToUpdate.setCard_number(form.getCardNumber());
			formToUpdate.setCard_type_id(form.getCardType());
			formToUpdate.setCbu(form.getCbu());
			formToUpdate.setCity(form.getCity());
			formToUpdate.setCompleted(true);
			formToUpdate.setCuil_cuit(form.getCuil_cuit());
			formToUpdate.setDid_you_know_msf(form.isDidYouKnowMsf());
			formToUpdate.setDni(form.getDni());
			formToUpdate.setEmail(form.getEmail());
			formToUpdate.setMobile_number(form.getMobileNumber());
			formToUpdate.setMonthly_amount_contribution(form.getMonthlyAmountContribution());
			formToUpdate.setObservations(form.getObservations());
			formToUpdate.setPostcode(form.getPostcode());
			formToUpdate.setProvince(form.getProvince());
			formRepository.save(formToUpdate);
			return ResponseEntity.status(HttpStatus.OK).body(formToUpdate);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("No existe formulario para el dni: " + form.getDni());
		}
	}

	/**
	 * Almacenar los arrays de forms provenientes de la app mobile
	 * 
	 * @param listOfForm
	 * @return
	 */

	@PostMapping("/form")
	public ResponseEntity<?> createFormFromMobile(@RequestBody ArrayList<FormRequestMobile> listOfForm) {
		List<Form> formsSaved = new ArrayList<>();
		for (FormRequestMobile inputForm : listOfForm) {
			Form form = formService.createForm(inputForm);
			formsSaved.add(form);
		}
		return ResponseEntity.status(HttpStatus.OK).body(formsSaved);
	}

	/**
	 * Obtener el formulario de un socio a tráves de su DNI
	 * 
	 * @Input int dni
	 * @Return Form formulario
	 */

	@GetMapping("/get-form/{dni}")
	public Form getFormByDni(@PathVariable int dni) {
		return formService.getFormByDni(dni);
	}

	/**
	 * Obtener los formularios de un día
	 * 
	 * @param fecha
	 * @return List<Form>
	 * @throws ParseException
	 */
	@GetMapping("/get-form-by-date/{fecha}")
	public ResponseEntity<?> getFormsByDate(@PathVariable String fecha) throws ParseException {
		Date nuevaFecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
		System.out.println(nuevaFecha);
		List<Form> formularios = formService.getFormByDate(nuevaFecha);
		if (formularios.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("No existen formularios para la fecha " + nuevaFecha);
		}
		return ResponseEntity.status(HttpStatus.OK).body(formularios);
	}

	@GetMapping("/DniVerification/{dni}")
	public ResponseEntity<?> dniVerification(@PathVariable int dni) {
		Optional<Form> form = formRepository.findByDni(dni);
		if (!form.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body(dni);
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dni);
	}

}
