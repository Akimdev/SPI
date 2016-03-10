package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.dao.ElementConstitutifRepository;

/**
 * @author Kenza ABOUAKIL
 *
 */
@RestController
public class ElementConstitutifController {

	@Autowired
	private ElementConstitutifRepository elementConstitutifRepository;

	@RequestMapping(value = "/elementConstitutif/findByCodeFormation/{codeFormation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ElementConstitutif> findByCodeFormation(@PathVariable("codeFormation") String codeFormation) {
		return elementConstitutifRepository.findByCodeFormation(codeFormation);
	}
}
