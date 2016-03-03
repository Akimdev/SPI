package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.PromotionService;

/**
 * 
 * @author ASSABBAR classe Controle du service PromotionService
 */
@RestController
public class PromotionController {
	/**
	 * 
	 */
	@Autowired
	private PromotionService prommotionService;

	/**
	 * la methode permet de Listes toutes les promotions
	 */

	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return prommotionService.getPromotionALL();
	}

	/**
	 * 
	 * @param promotionPK
	 *            la methode permet de supprimer une promotion par anneUniversitaire et codeFormation
	 */
	@RequestMapping(value = "/deletePromotion", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deletePromotionPK(@RequestBody final PromotionPK promotionPK) {
		prommotionService.deletePromotion(promotionPK);
	}
}
