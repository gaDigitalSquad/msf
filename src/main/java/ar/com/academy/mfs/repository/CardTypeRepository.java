package ar.com.academy.mfs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.academy.mfs.model.CardType;

@Repository
public interface CardTypeRepository  extends JpaRepository<CardType, Integer>{
	CardType findByDescription(String description);
	CardType findByCode(String code);
	CardType findByCardTypeId(long cardTypeId);
}
