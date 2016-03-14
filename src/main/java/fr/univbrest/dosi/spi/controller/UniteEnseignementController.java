package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.service.UniteEnseignementService;

@RestController
public class UniteEnseignementController {

	@Autowired
	UniteEnseignementService ueServ;

	@RequestMapping(value = "/ajoutUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void ajoutUniteEnseignement(@RequestBody final UniteEnseignement uniteEnseignement) {
		ueServ.addUnitEnseignement(uniteEnseignement);
	}

	@RequestMapping(value = "/supprimerUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteUniteEnseignement(@RequestBody final UniteEnseignementPK uniteEnseignementPK) {
		ueServ.deletUnitEnseignement(uniteEnseignementPK);
	}

	/**
	 * @author Kenza ABOUAKIL
	 * @param codeFormation
	 * @return
	 */
	@RequestMapping(value = "/UE/findByCodeFormation/{codeFormation}", method = RequestMethod.GET)
	public final List<UniteEnseignement> findByCodeFormation(@PathVariable(value = "codeFormation") final String codeFormation) {
		return ueServ.findByCodeFormation(codeFormation);
	}

	@RequestMapping(value = "/UEs")
	public List<UniteEnseignement> getAllUEs() {
		return ueServ.getAllUEs();
	}

	@RequestMapping(value = "/getUEByEnseignant-{noEnseignant}", method = RequestMethod.GET)
	public final List<UniteEnseignement> getUEByEnseignant(@PathVariable(value = "noEnseignant") final Integer noEnseignant) {
		return ueServ.getUEByEnseignant(noEnseignant);
	}

	@RequestMapping(value = "/nombreUEs", headers = "Accept=application/json")
	public int nombreUEs() {
		return ueServ.nombreUEs();
	}
	
	/**
	 * @author Youssef
	 * @param uniteEnseignementPK
	 * @return
	 */
	@RequestMapping(value = "/getUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public UniteEnseignement getUeById(@RequestBody final UniteEnseignementPK uniteEnseignementPK) {
		return ueServ.uniteEnseignement(uniteEnseignementPK);
	}

}
