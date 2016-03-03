package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
/**
 * 
 * @author ASSABBAR 
 *classe Controle du service PromotionService
 */
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class PromotionController {

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private EtudiantService etudiantservice;

	/**
	 * Soukaina BAQLOUL
	 * 
	 * @param noEnseignant
	 * @return
	 */

	// @RequestMapping(value = "/getPromotion/{anneeUniversitaire}/{codeFormation}",produces="application/json")
	// public final Promotion getPromotion(@PathVariable(value = "anneeUniversitaire") final String anneeUniversitaire, @PathVariable(value = "codeFormation") final String codeFormation) {
	// return promotionservice.getPromotion(new PromotionPK(codeFormation, anneeUniversitaire));
	// }

	@RequestMapping(value = "/getPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Promotion getPromotion(@RequestBody PromotionPK promotionPK) {
		return promotionService.getPromotion(promotionPK);
	}

	/**
	 * soukaina BAQLOUL
	 * 
	 * @param promotionPK
	 * @return
	 */
	@RequestMapping(value = "/getEtudiantByPromotion/{anneeUniversitaire}/{codeFormation}", produces = "application/json")
	public final List<Etudiant> getEtudiantPromotion(@PathVariable(value = "anneeUniversitaire") final String anneeUniversitaire, @PathVariable(value = "codeFormation") final String codeFormation) {
		return etudiantservice.getEtudiantByPromotion(new PromotionPK(codeFormation, anneeUniversitaire));
	}

	/**
	 * ASSABBAR la methode permet de Lister toutes les promotions
	 *
	 * 
	 */

	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return promotionService.getPromotionALL();
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
}
