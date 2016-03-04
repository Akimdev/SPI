package fr.univbrest.dosi.spi.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class PromotionController {

	@Autowired
	private PromotionService promotionService;
	
	@Autowired
	private EtudiantService etudiantservice;

	/**
	 * Author Soukaina
	 * Cette fontion fait l'ajout d'une promotion
	 * @param promotion
	 * @return
	 */
	  
	/* @RequestMapping(value = "/addPromotion", method = RequestMethod.POST, consumes ="application/json;charset=UTF-8")
	  public final  String addPromotion(@RequestBody  ProEns proEns) {
		System.out.println(proEns.getNom()); 
		  
    	return proEns.getNom();*/

	/**
	 * @author ASSABBAR
	 *
	 * @param promotionPK
	 *            la methode permet de supprimer une promotion par anneUniversitaire et codeFormation
	 */
	@RequestMapping(value = "/deletePromotion", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deletePromotionPK(@RequestBody final PromotionPK promotionPK) {
		promotionService.deletePromotion(promotionPK);
	}

	/**
	 * @author soukaina BAQLOUL
	 *
	 * @param promotionPK
	 * @return la liste des étudiants correspondants à une promotion
	 */

	@RequestMapping(value = "/getEtudiantByPromotion", method = RequestMethod.POST, headers = "Accept=application/json", produces={ MediaType.APPLICATION_JSON_VALUE })
    public final List<Etudiant> getEtudiantPromotion(@RequestBody PromotionPK promotionPK) {
	    return etudiantservice.getEtudiantByPromotion(promotionPK);

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
	 * @author ASSABBAR
	 *
	 *         la methode permet de Lister toutes les promotions
	 */

	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return promotionService.getPromotionALL();
	}

	/**
	 * Soukaina
	 *
	 * @param promotion
	 *            cette methode fait la modification d'une promotion
	 * @return
	 */
	@RequestMapping(value = "/updatePromotion", method = RequestMethod.POST, consumes = { "application/json;charset=UTF-8" }, produces = { "application/json;charset=UTF-8" })
	public final String updatePromotion(@RequestBody final Promotion promotion) {
		promotionService.update(promotion);
		return "la promotion " + promotion.getSiglePromotion() + " " + promotion.getPromotionPK().getCodeFormation() + " " + promotion.getPromotionPK().getCodeFormation() + "est modifiée :D";
	}

}
