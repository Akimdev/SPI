package fr.univbrest.dosi.spi.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.UniteEnseignement;
import fr.univbrest.dosi.spi.bean.User;
import fr.univbrest.dosi.spi.service.EnseignantService;
import fr.univbrest.dosi.spi.service.PromotionService;
import fr.univbrest.dosi.spi.service.UniteEnseignementService;

/**
 * @author DOSI
 *
 */

@RestController
public class EnseignantController {

	@Autowired
	private EnseignantService enseignantService;

	@Autowired
	EnseignantService ensService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private UniteEnseignementService uniteEnseignementService;

	@Autowired
	User user;

	/**
	 *
	 * @param enseignant
	 *            l'entité de l'enseignant
	 * @return le message d'ajout
	 */
	 
	@RequestMapping(value = "/ajouterEnseignant", method = RequestMethod.POST, consumes = { "application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })
	public final void addEnseignant(@RequestBody final Enseignant enseignant) {
		enseignantService.addEnseignant(enseignant);
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 */
	@RequestMapping(value = "/deleteEnseignant/{noenseignant}")
	public final void deleteEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		enseignantService.deleteEnseignant(noEnseignant);
	}

	/**
	 *
	 * @return liste des enseignant
	 */
	@RequestMapping("/ens")
	public final Iterable<Enseignant> enseignant() {
		return enseignantService.listens();
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return un boolean
	 */
	@RequestMapping(value = "/existens/{noenseignant}")
	public final Boolean existEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		return enseignantService.existEnseignant(noEnseignant);
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return un enseignant
	 */

	@RequestMapping(value = "/getens/{noenseignant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Enseignant getEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		return enseignantService.getEnseignant(noEnseignant);
	}

	/**
	 *
	 * @param nom
	 *            de recherche pour un enseignant
	 * @return list des enseignant ayant le parmetre nom
	 */
	 
	@RequestMapping(value = "/getensnom/{nom}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final List<Enseignant> getEnseignantByNom(@PathVariable(value = "nom") final String nom) {
		return enseignantService.getEnseignantByNom(nom);
	}

	public EnseignantService getEnseignantService() {
		return enseignantService;
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return liste des promotions
	 */
	@RequestMapping(value = "/getpromotionenseignant/{noenseignant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final List<Promotion> getPromotionEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		return promotionService.getPromotionByEnseignant(noEnseignant);
	}

	public PromotionService getPromotionService() {
		return promotionService;
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return liste des unite enseignant
	 */

	@RequestMapping("/getuebyenseignant/{noenseignant}")
	public final List<UniteEnseignement> getUEByEnseignant(@PathVariable("noenseignant") final Integer noEnseignant) {
		return uniteEnseignementService.getUEByEnseignant(noEnseignant);
	}

	public UniteEnseignementService getUniteEnseignementService() {
		return uniteEnseignementService;
	}

	@RequestMapping(value = "/nombreEnseignants")
	public int nombreEnseignants() {
		return enseignantService.nombreEnseignants();
	}

	public void setEnseignantService(final EnseignantService enseignantService) {
		this.enseignantService = enseignantService;
	}

	public void setPromotionService(final PromotionService promotionService) {
		this.promotionService = promotionService;
	}

	public void setUniteEnseignementService(final UniteEnseignementService uniteEnseignementService) {
		this.uniteEnseignementService = uniteEnseignementService;
	}

	/**
	 *
	 * @param enseignant
	 *            objet
	 * @return message de modification
	 */
	 
	@RequestMapping(value = "/updateEnseignant", method = RequestMethod.POST, consumes = { "application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })
	public final void updateEnseignant(@RequestBody final Enseignant enseignant) {
		enseignantService.updateEnseignant(enseignant);
	}

	/**
	 * @author Othman
	 * @param noEnseignant
	 * @return Cette méthode retourne une liste triée d'unités d'enseignement
	 * 
	 */
	 
	@RequestMapping(value = "/getUEByNoEnseignant", produces = { "application/json;charset=UTF-8" })
	public List<UniteEnseignement> getUEByNoEnseignant(HttpServletRequest request) {
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant ens = auth.getNoEnseignant();
		return enseignantService.getUEByNoEnseignant(ens.getNoEnseignant());
	}
}