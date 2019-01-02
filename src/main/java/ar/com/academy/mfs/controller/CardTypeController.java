package ar.com.academy.mfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.academy.mfs.model.CardType;
import ar.com.academy.mfs.service.CardTypeService;

@RestController
public class CardTypeController {

	@Autowired
	CardTypeService cardTypeService;
	
	@GetMapping("/card_type/{card_type_id}")
	public CardType getCardTypeById(@PathVariable long card_type_id) {
		return cardTypeService.getCardTypeById(card_type_id);
	}
	
	@GetMapping("/card_type/{description}")
	public CardType getCardTypeByDescription(@PathVariable String description) {
		return cardTypeService.getCardTypeByDescription(description);
	}
	
	@GetMapping("/card_type/{code}")
	public CardType getCardTypeByCode(@PathVariable String code) {
		return cardTypeService.getCardTypeByCode(code);
	}
	
	
}
