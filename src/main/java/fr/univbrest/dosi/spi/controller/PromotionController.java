package fr.univbrest.dosi.spi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.bean.utils.ProEns;
import fr.univbrest.dosi.spi.service.EnseignantService;
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.FormationService;
import fr.univbrest.dosi.spi.service.PromotionService;

/**
 *
 * @author hakim
 *
 */
@RestController
public class PromotionController {

	@Autowired
	private EnseignantService enseignantService;

	@Autowired
	private EtudiantService etudiantservice;

	@Autowired
	FormationService formationservice;

	@Autowired
	private PromotionService promotionService;

	/**
	 * cet méthod permet d'ajouter une promotion author : hakim
	 *
	 * @param proEns
	 * @return
	 */
	@RequestMapping(value = "/addPromotion", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody void addPromotion(@RequestBody ProEns proEns) {
		/** récupération de la promotion à créer ! */
		Promotion promotion = proEns.getPromotion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Formation formationExistante = formationservice.getFormation(proEns.getPromotion().getPromotionPK().getCodeFormation());
		Enseignant enseignantExistante = proEns.getEnseignant();
		if (enseignantExistante.getNoEnseignant() != null) {
			enseignantExistante = enseignantService.getEnseignant(proEns.getEnseignant().getNoEnseignant());
			promotion.setNoEnseignant(enseignantExistante);
		}
		/**
		 * construction de l'objet promotion avec formation et enseignant reçu de JSON
		 */
		promotion.setFormation(formationExistante);
		/** ajout de la promotion */
		promotionService.addPromotion(promotion);
	}

	/**
	 * ASSABBAR
	 *
	 * @param promotionPK
	 *            la methode permet de supprimer une promotion par anneUniversitaire et codeFormation
	 */
	@RequestMapping(value = "/deletePromotion", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deletePromotionPK(@RequestBody final PromotionPK promotionPK) {
		promotionService.deletePromotion(promotionPK);
	}

	/**
	 * @author Kenza ABOUAKIL
	 */
	@RequestMapping(value = "/promotion/findByCodeFormation/{codeFormation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<Promotion> findByCodeFormation(@PathVariable("codeFormation") String codeFormation) {
		return promotionService.findByCodeFormation(codeFormation);
	}

	/**
	 * @author Kenza ABOUAKIL
	 * @param l
	 *            'identifiant d'une promotion (codeFormation + anneeUniversitaire)
	 * @return le numéro de l'enseignant responsable d'une promotion
	 */
	@RequestMapping(value = "/promotion/getEnseignantResponsable", method = RequestMethod.POST, headers = "Accept=application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Enseignant getEnseignantResponsable(@RequestBody PromotionPK promotionPK) {
		return promotionService.getEnseignantResponsable(promotionPK);
	}

	/**
	 * @author soukaina BAQLOUL
	 *
	 * @param promotionPK
	 * @return la liste des étudiants correspondants à une promotion
	 */

	@RequestMapping(value = "/getEtudiantByPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final List<Etudiant> getEtudiantPromotion(@RequestBody PromotionPK promotionPK) {
		return etudiantservice.getEtudiantByPromotion(promotionPK);

	}

	@RequestMapping(value = "/getPromoByNoEnseignant", produces = { "application/json;charset=UTF-8" })
	public List<Promotion> getPromoByNoEnseignant(final HttpServletRequest request) {
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant ens = auth.getNoEnseignant();
		return promotionService.getPromoByNoEnseignant(ens.getNoEnseignant());
	}

	/**
	 * @author Soukaina BAQLOUL
	 *
	 * @param noEnseignant
	 */

	@RequestMapping(value = "/getPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Promotion getPromotion(@RequestBody PromotionPK promotionPK) {
		return promotionService.getPromotion(promotionPK);
	}

	/**
	 * @author Souakaina ASSABAR, Kenza ABOUAKIL
	 *
	 *         la methode permet de Lister toutes les promotions
	 */

	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return promotionService.getPromotionALL();
	}

	/**
	 * @author Zouhair La recuperation du nombre des promotions
	 *
	 * @param idRubrique
	 * @return
	 */

	@RequestMapping(value = "/nombrePromotions")
	public int nombrePromotions() {
		return promotionService.nombrePromotions();
	}

	/**
	 *
	 * @param proEns
	 */

	@RequestMapping(value = "/updatePromotion", method = RequestMethod.POST, headers = "Accept=application/json")
	public void updatePromotion(@RequestBody ProEns proEns) {
		/** récupération de la promotion à créer ! */
		Promotion promotion = proEns.getPromotion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Formation formationExistante = formationservice.getFormation(proEns.getPromotion().getPromotionPK().getCodeFormation());
		Enseignant enseignantExistante = proEns.getEnseignant();
		if (enseignantExistante.getNoEnseignant() != null) {
			enseignantExistante = enseignantService.getEnseignant(proEns.getEnseignant().getNoEnseignant());
			promotion.setNoEnseignant(enseignantExistante);
		}
		/**
		 * construction de l'objet promotion avec formation et enseignant reçu de JSON
		 */
		promotion.setFormation(formationExistante);
		/** ajout de la promotion */
		promotionService.update(promotion);
	}
}