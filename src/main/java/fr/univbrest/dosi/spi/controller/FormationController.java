package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.service.FormationService;

/**
 * @author Kenza ABOUAKIL
 *
 */
@RestController
public class FormationController {

	@Autowired
	FormationService formationService;

	// createFormation
	@RequestMapping(value = "/formation/createFormation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void createEnseignant(@RequestBody Formation formation) {
		formationService.createFormation(formation);
	}

	@RequestMapping(value = "/formation/deleteFormation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteEnseignant(String codeFormation) {
		formationService.deleteFormation(codeFormation);
	}

	@RequestMapping(value = "/formations", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Formation> findAll() {
		return formationService.findAll();
	}

	@RequestMapping(value = "/formation/formationParCodeFormation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Formation findByCodeFormation(String codeFormation) {
		return formationService.getFormation(codeFormation);
	}

	@RequestMapping(value = "/formation/formationParNomFormation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Formation> findByNomFormation(String nomFormation) {
		return formationService.findByNomFormation(nomFormation);
	}

	@RequestMapping(value = "/formation/getNomFormation", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getNomFormation(String codeFormation) {
		return formationService.getNomFormation(codeFormation);
	}

	@RequestMapping(value = "/formation/getNomFormations", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<String> getNomFormations(@RequestBody List<String> codeFormations) {
		return formationService.getNomFormations(codeFormations);
	}
	
	/**
	 * @author Othman
	 * controlleur pour retourner le nombre de formations
	 */
	@RequestMapping(value="/nombreFormations", headers = "Accept=application/json")
	public int nombreFormations(){
		return formationService.nombreFormations();
	}
}
