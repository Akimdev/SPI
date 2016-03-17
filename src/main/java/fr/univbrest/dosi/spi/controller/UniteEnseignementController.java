package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.ElementConstitutif;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.UniteEnseignementPK;
import fr.univbrest.dosi.spi.bean.utils.UniteEnseignementUtil;
import fr.univbrest.dosi.spi.service.EnseignantService;
import fr.univbrest.dosi.spi.service.FormationService;
import fr.univbrest.dosi.spi.service.UniteEnseignementService;

/**
 * 
 * @author Othman
 *
 */
@RestController
public class UniteEnseignementController {

	@Autowired
	UniteEnseignementService ueServ;
	
	@Autowired
	EnseignantService enseignantService;
	
	@Autowired
	FormationService formationService;

	@RequestMapping(value = "/addUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void addUE(@RequestBody final UniteEnseignementUtil ueUtil) {
		Enseignant enseignant = enseignantService.getEnseignant(ueUtil.getEnseignant().getNoEnseignant());
		Formation formation = formationService.getFormation(ueUtil.getUniteEnseignement().getUniteEnseignementPK().getCodeFormation());
		UniteEnseignement ue = ueUtil.getUniteEnseignement();
		ue.setFormation(formation);
		ue.setNoEnseignant(enseignant);
		ueServ.addUE(ue);
	}
	
	@RequestMapping(value = "/updateUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void updateUE(@RequestBody final UniteEnseignementUtil ueUtil) {
		Enseignant enseignant = enseignantService.getEnseignant(ueUtil.getEnseignant().getNoEnseignant());
		Formation formation = formationService.getFormation(ueUtil.getUniteEnseignement().getUniteEnseignementPK().getCodeFormation());
		UniteEnseignement ue = ueUtil.getUniteEnseignement();
		ue.setFormation(formation);
		ue.setNoEnseignant(enseignant);
		ueServ.updateUE(ue);
	}

	@RequestMapping(value = "/deleteUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteUE(@RequestBody final UniteEnseignementPK uniteEnseignementPK) {
		ueServ.deleteUE(uniteEnseignementPK);
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

	@RequestMapping(value = "/getUE", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public UniteEnseignement getUEById(@RequestBody final UniteEnseignementPK uniteEnseignementPK) {
		return ueServ.getUE(uniteEnseignementPK);
	}

	@RequestMapping(value="/getECByUE", method = RequestMethod.POST, headers = "Accept=application/json", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ElementConstitutif> getECByUE(@RequestBody final UniteEnseignementPK uniteEnseignementPK){
		return ueServ.getECByUE(uniteEnseignementPK);
	}
}