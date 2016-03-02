package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.service.PromotionService;

/**
 * 
 * @author ASSABBAR
 *
 */
@RestController
public class PromotionController {
	/**
	 * 
	 */
	@Autowired
	private PromotionService prommotionService;

	/**
	 * Liste des promotions
	 */

	@RequestMapping(value = "/promotions", produces = { org.springframework.http.MediaType.APPLICATION_JSON_VALUE })
	public Iterable<Promotion> getPromotions() {
		return prommotionService.getPromotionALL();
	}
}
