package fr.univbrest.dosi.spi.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.service.FormationService;

/**
 * @author Kenza ABOUAKIL
 *
 */

@RestController
public class FormationController {

	@Autowired
	FormationService formationService;

	/*
	 * @Inherited
	 */
	@RequestMapping(value = "/formation/addFormation", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody void addFormation(@RequestBody Formation formation) {
		formationService.addFormation(formation);
	}

	/*
	 * @Inherited
	 */
	@RequestMapping(value = "/formation/deleteFormation/{codeFormation}", headers = "Accept=application/json")
	public void deleteFormation(@PathVariable("codeFormation") String codeFormation) {
		formationService.deleteFormation(codeFormation);
	}

	/*
	 * @Author Kenza ABOUAKIL
	 * 
	 * @Inherited
	 */
	@RequestMapping(value = "/formations", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Formation> getAllFormation() {
		List formations = formationService.getAllFormation();
		Collections.sort(formations, new Comparator<Formation>() {
			@Override
			public int compare(Formation f1, Formation f2) {
				return f1.getCodeFormation().compareTo(f2.getCodeFormation());
			}
		});
		return formations;
	}

	/*
	 * @Inherited
	 */
	@RequestMapping(value = "/formation/getFormation/{codeFormation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Formation getformation(@PathVariable(value = "codeFormation") String codeFormation) {
		return formationService.getFormation(codeFormation);
	}

	/*
	 * @Inherited
	 */
	@RequestMapping(value = "/formation/getNomFormation/{codeFormation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public String getNomFormation(@PathVariable(value = "codeFormation") String codeFormation) {
		return formationService.getNomFormation(codeFormation);
	}

	@RequestMapping(value = "/formation/getNomFormations", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<String> getNomFormations(@RequestBody List<String> codeFormations) {
		return formationService.getNomFormations(codeFormations);
	}

	/*
	 * @Inherited
	 */

	/**
	 * @author othman
	 * @param codeFormation
	 * @return Cette méthode retourne une liste d'unités d'enseignement en lui donnant comme paramètre un code de formation
	 **/
	@RequestMapping(value = "/getUEsByFormation-{codeFormation}", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<UniteEnseignement> getUEsByCodeFormation(@PathVariable("codeFormation") String codeFormation) {
		return formationService.getUEsByCodeFormation(codeFormation);
	}

	/**
	 * @author Othman controlleur pour retourner le nombre de formations
	 */
	@RequestMapping(value = "/nombreFormations", headers = "Accept=application/json")
	public long nombreFormations() {
		return formationService.nombreFormations();
	}

	/**
	 * @author Othman
	 *
	 *         Modification d'une formation existante
	 * @param formation
	 *            : la formation à modifier
	 */
	@RequestMapping(value = "/formation/updateFormation", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody void updateFormation(@RequestBody Formation formation) {
		formationService.updateFormation(formation);
	}
}