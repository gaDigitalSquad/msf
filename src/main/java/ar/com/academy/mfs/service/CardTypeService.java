package ar.com.academy.mfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.academy.mfs.model.CardType;
import ar.com.academy.mfs.repository.CardTypeRepository;

@Service("cardTypeService")
public class CardTypeService {
	
	@Autowired 
	CardTypeRepository cardTypeRepository;
	

	public CardType getCardTypeById(long cardTypeId) {
		return cardTypeRepository.findByCardTypeId(cardTypeId);
	}
	
	public CardType getCardTypeByCode(String code) {
		return cardTypeRepository.findByCode(code);
	}
	
	public CardType getCardTypeByDescription(String description) {
		return cardTypeRepository.findByDescription(description);
	}
	
	
	

}
