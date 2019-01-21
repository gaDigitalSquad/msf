package ar.com.academy.mfs.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.CardType;
import ar.com.academy.mfs.model.Form;
import ar.com.academy.mfs.model.User;
import ar.com.academy.mfs.model.Zone;
import ar.com.academy.mfs.repository.CardTypeRepository;
import ar.com.academy.mfs.repository.FormRepository;
import ar.com.academy.mfs.repository.UserRepository;
import ar.com.academy.mfs.repository.ZoneRepository;
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
	
	@PostMapping("/formBack")
	public ResponseEntity<?> createForm(@RequestBody ArrayList<FormRequest> listOfForm) {
		List<Form> formsSaved = new ArrayList<>();
		for(FormRequest inputForm: listOfForm) {
			//System.out.println("\n\n\nHasta el tipo de carta llega 1\n\n\n");
			User user = userRepository.findByUsername(inputForm.getCompletedByUser());
			if(user == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
			//System.out.println("\n\n\nHasta el tipo de carta llega 2\n\n\n");
			Zone zone = zoneRepository.findByZoneName(inputForm.getZone());
			if(zone == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid zone");
			//System.out.println("\n\n\nHasta el tipo de carta llega 3\n\n\n");
			//CardType cardType = cardTypeRepository.findByCode(inputForm.getCardType());
			//if(cardType == null) 
				//return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid card type");
			CardType cardType = cardTypeRepository.findByCardTypeId(inputForm.getCardType());
			if(cardType == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid card type");
			//System.out.println("\n\n\nHasta el tipo de carta llega 4\n\n\n");
			Form form = formService.createForm(inputForm, user.getUser_id(), zone.getZoneId(), cardType.getCardTypeId());
			formsSaved.add(form);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(formsSaved);
	}
	
	@PostMapping("/form")
	public ResponseEntity<?> createFormFromMobile(@RequestBody ArrayList<FormRequestMobile> listOfForm){
		List<Form> formsSaved = new ArrayList<>();
		for(FormRequestMobile inputForm: listOfForm) {
			User user = userRepository.findByUsername(inputForm.getCompletedByUser());
			if(user == null) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user");
			Form form = formService.createForm(inputForm, user.getUser_id());
			formsSaved.add(form);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(formsSaved);
	}
	
	@GetMapping("/get-form/{dni}")
	public Form getFormByDni(@PathVariable int dni) {
		return formService.getFormByDni(dni);
	
	}
	
	@PutMapping("/form/{dni}")
	public ResponseEntity<?> updateFormByDni(@PathVariable int dni, @RequestBody Form form){
		Form formUpdated = formService.updateFormDni(dni, form);
		return new ResponseEntity<Form>(formUpdated, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/DniVerification/{dni}")
	public ResponseEntity<?> dniVerification(@PathVariable int dni) {
		Optional<Form> form = formRepository.findByDni(dni);
		if(form.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body("El dni no existe");
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El dni ya existe");
	}

}
