package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.ProEns;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.EnseignantService;
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.FormationService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class PromotionControler {

	@Autowired
	private PromotionService promotionService;
	@Autowired
	private EtudiantService etudiantservice;
	@Autowired
	FormationService formationservice;
	@Autowired
	private EnseignantService enseignantService;

	/**
	 * Author Soukaina Cette fontion fait l'ajout d'une promotion
	 * 
	 * @param promotion
	 * @return
	 */
	@RequestMapping(value = "/getPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public final Promotion getPromotion(@RequestBody PromotionPK promotionPK) {
		return promotionService.getPromotion(promotionPK);
	}

	@RequestMapping(value = "/getEtudiantByPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public final List<Etudiant> getEtudiantPromotion(@RequestBody PromotionPK promotionPK) {
		return etudiantservice.getEtudiantByPromotion(promotionPK);
	}

	/**
	 * Assabar Liste des promotions
	 */
	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return promotionService.getPromotionALL();
	}

	/**
	 * cet méthod permet d'ajouter une promotion author : hakim
	 * 
	 * @param proEns
	 * @return
	 */
	@RequestMapping(value = "/ajoutPromotion", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody String addTest(@RequestBody ProEns proEns) {
		/** récupération de la promotion à créer ! */
		Promotion promotion = proEns.getPromotion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Formation formationExistante = formationservice.getFormation(proEns.getFormation().getCodeFormation());
		Enseignant enseignantExistante = enseignantService.getEnseignant(proEns.getEnseignant().getNoEnseignant());
		/**
		 * construction de l'objet promotion avec formation et enseignant reçu
		 * de JSON
		 */
		promotion.setNoEnseignant(enseignantExistante);
		promotion.setFormation(formationExistante);
		/** ajout de la promotion */
		promotionService.addPromotion(promotion);
		return "succés";
	}
}
