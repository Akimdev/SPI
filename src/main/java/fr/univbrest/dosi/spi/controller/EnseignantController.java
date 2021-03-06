package fr.univbrest.dosi.spi.controller;

import java.util.Collections;
import java.util.Comparator;
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

	/*
	 * private enum TypeDroit { CREATE, DELETE, MODIFY, SELECT, }
	 * 
	 * /** Service de gestion des enseignants
	 */
	@Autowired
	EnseignantService ensService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private UniteEnseignementService uniteEnseignementService;

	// private final Map<TypeDroit, List<Role>> mapDroits = new HashMap<EnseignantController.TypeDroit, List<Role>>();

	@Autowired
	User user;

	/*
	 * public EnseignantController() { this.configure(); }
	 * 
	 * private void checkDroits(final TypeDroit typeDroit) { if (!this.mapDroits.get(typeDroit).contains(this.user.getRoles())) { throw new SPIException(SpiExceptionCode.NOT_ENOUGH_RIGHT,
	 * "L'utilisateur n'a pas les droits suffisants pour effectuer l'action demandée"); } }
	 * 
	 * private void configure() { this.mapDroits.put(TypeDroit.CREATE, Arrays.asList(Role.ADMIN, Role.PROF)); this.mapDroits.put(TypeDroit.SELECT, Arrays.asList(Role.ETUDIANT, Role.ADMIN, Role.PROF));
	 * this.mapDroits.put(TypeDroit.MODIFY, Arrays.asList(Role.ADMIN, Role.PROF)); this.mapDroits.put(TypeDroit.DELETE, Arrays.asList(Role.ADMIN)); }
	 */

	/**
	 *
	 * @param enseignant
	 *            l'entité de l'enseignant
	 * @return le message d'ajout
	 */

	// @RequestMapping(value="/ajouterEnseignant" , headers="Accept=application/json", method=RequestMethod.POST)
	@RequestMapping(value = "/ajouterEnseignant", method = RequestMethod.POST, consumes = { "application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })
	public final void addEnseignant(@RequestBody final Enseignant enseignant) {
		// this.checkDroits(TypeDroit.CREATE);
		enseignantService.addEnseignant(enseignant);

	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 */
	@RequestMapping(value = "/deleteEnseignant/{noenseignant}")
	public final void deleteEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		// this.checkDroits(TypeDroit.DELETE);
		enseignantService.deleteEnseignant(noEnseignant);
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
	 * @Author Kenza ABOUAKIL
	 * @return liste des enseignant
	 */
	@RequestMapping("/ens")
	public final Iterable<Enseignant> getAllEnseignant() {
		List enseignants = (List) enseignantService.listens();
		Collections.sort(enseignants, new Comparator<Enseignant>() {
			@Override
			public int compare(Enseignant e1, Enseignant e2) {
				return e1.getNom().compareTo(e2.getNom());
			}
		});
		return enseignants;
	}

	/**
	 *
	 * @param noEnseignant
	 *            l'id de l'enseignant
	 * @return un enseignant
	 */
	@RequestMapping(value = "/getens/{noenseignant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Enseignant getEnseignant(@PathVariable(value = "noenseignant") final Integer noEnseignant) {
		// this.checkDroits(TypeDroit.SELECT);
		return enseignantService.getEnseignant(noEnseignant);
	}

	/**
	 *
	 * @param nom
	 *            de recherche pour un enseignant
	 * @return list des enseignant ayant le parmetre nom
	 */

	// @RequestMapping(value ="/getens/{id}")
	@RequestMapping(value = "/getensnom/{nom}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final List<Enseignant> getEnseignantByNom(@PathVariable(value = "nom") final String nom) {
		// this.checkDroits(TypeDroit.SELECT);
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

	// @RequestMapping(value="/ajouterEnseignant" , headers="Accept=application/json", method=RequestMethod.POST)
	@RequestMapping(value = "/updateEnseignant", method = RequestMethod.POST, consumes = { "application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })
	public final void updateEnseignant(@RequestBody final Enseignant enseignant) {
		// this.checkDroits(TypeDroit.MODIFY);
		enseignantService.updateEnseignant(enseignant);

	}
}
