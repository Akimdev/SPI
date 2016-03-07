package fr.univbrest.dosi.spi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.dao.RubriqueRepository;

@Service
public class RubriqueService {

	
	@Autowired
	RubriqueRepository rubriqueRepository;
	

	public RubriqueRepository getRubriqueRepository() {
		return rubriqueRepository;
	}


	public void setRubriqueRepository(RubriqueRepository rubriqueRepository) {
		this.rubriqueRepository = rubriqueRepository;
	}


	public final Iterable<Rubrique> getRubriqueALL() {
		final Iterable<Rubrique> rubriques = rubriqueRepository.findAll();
		return rubriques;
	}

}
