package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.service.RubriqueService;

/**
 * @author DOSI
 *
 */
@RestController
public class RubriqueController {

	@Autowired
	RubriqueService rubriqueService;
	
	
	@RequestMapping(value = "/rubriques", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Rubrique> getRubriques() {
		return rubriqueService.getRubriqueALL();
	}

}
