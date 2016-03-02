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
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class PromotionController {
	
	
	@Autowired 
	private PromotionService promotionservice;
	
	@Autowired
	private EtudiantService etudiantservice;
	
	/**
	 * Soukaina
	 * @param noEnseignant
	 * @return
	 */
	 
//	@RequestMapping(value = "/getPromotion/{anneeUniversitaire}/{codeFormation}",produces="application/json")
//	public final Promotion getPromotion(@PathVariable(value = "anneeUniversitaire") final String anneeUniversitaire, @PathVariable(value = "codeFormation") final String codeFormation) {
//		return promotionservice.getPromotion(new PromotionPK(codeFormation, anneeUniversitaire));
//	}
	
	
	@RequestMapping(value = "/getPromotion",method = RequestMethod.POST, headers = "Accept=application/json", produces={ MediaType.APPLICATION_JSON_VALUE })
	public final Promotion getPromotion(@RequestBody PromotionPK promotionPK) {
		return promotionservice.getPromotion(promotionPK);
	}
	/**
	 * soukaina
	 * @param promotionPK
	 * @return
	 */
	@RequestMapping(value = "/getEtudiantByPromotion/{anneeUniversitaire}/{codeFormation}", produces="application/json")
	public final List<Etudiant> getEtudiantPromotion(@PathVariable(value = "anneeUniversitaire") final String anneeUniversitaire, @PathVariable(value = "codeFormation") final String codeFormation) {
		return etudiantservice.getEtudiantByPromotion(new PromotionPK(codeFormation, anneeUniversitaire));
	}

	
	
	
	
}
