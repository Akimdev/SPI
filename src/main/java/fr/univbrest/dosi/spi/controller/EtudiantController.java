package fr.univbrest.dosi.spi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Authentification;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.bean.utils.EtudiantPromotionUtil;
import fr.univbrest.dosi.spi.service.EtudiantService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class EtudiantController {

	@Autowired
	EtudiantService etudiantService;

	@Autowired
	PromotionService promotionService;
	/**
	 * @author Othman
	 * @param etudiant
	 * @return retourne un message de validation de l'ajout
	 */
	@RequestMapping(value="/etudiants/addEtudiant", method = RequestMethod.POST, headers= "Accept=application/json")
	public void addEtudiant(@RequestBody final EtudiantPromotionUtil etudiantPromotionUtil){
		Promotion promotion = promotionService.getPromotion(etudiantPromotionUtil.getPromotion().getPromotionPK());
		Etudiant etudiant = etudiantPromotionUtil.getEtudiant();
		etudiant.setPromotion(promotion);
		etudiantService.addEtudiant(etudiant);
	}
	/**
	 * @author Othman
	 * @param etudiant
	 * @return retourne un message de validation de la modification
	 */
	@RequestMapping(value="/etudiants/updateEtudiant", method = RequestMethod.POST, headers= "Accept=application/json")
	public void updateEtudiant(@RequestBody final EtudiantPromotionUtil etudiantPromotionUtil){
		Promotion promotion = promotionService.getPromotion(etudiantPromotionUtil.getPromotion().getPromotionPK());
		Etudiant etudiant = etudiantPromotionUtil.getEtudiant();
		etudiant.setPromotion(promotion);
		etudiantService.updateEtudiant(etudiant);
	}
	/**
	 * @author Othman
	 * @param noEtudiant
	 * @return retourne un message de validation de la suppression
	 */
	@RequestMapping(value="/etudiants/deleteEtudiant-{noEtudiant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteEtudiant(@PathVariable("noEtudiant")String noEtudiant){
		etudiantService.deleteEtudiant(noEtudiant);
	}
	/**
	 * 
	 * @return une liste de formation
	 */
	@RequestMapping(value = "/etudiants", produces = "application/json")
	public final Iterable<Etudiant> etudiant() {
		Iterable<Etudiant> liste = etudiantService.getAllEtudiant();
		for (Etudiant etd : liste) {
			System.out.println(etd.getNoEtudiant());
		}
		return etudiantService.getAllEtudiant();
	}

	/**
	 * 
	 * @param noEtudiant
	 * @return retourne un etudiant particulier
	 */
	@RequestMapping(value = "/etudiants/{noEtudiant}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public final Etudiant getEtudiant(@PathVariable(value = "noEtudiant") final String noEtudiant) {
		return etudiantService.getEtudiant(noEtudiant);
	}
	
	@RequestMapping(value="/nombreEtudiants", produces = { MediaType.APPLICATION_JSON_VALUE })
	public int nombreEtudiants(){
		return etudiantService.getNombreEtudiants();
	}
	
	@RequestMapping(value="/getPromoPKByEtudiant", produces = { "application/json;charset=UTF-8" })
	public final Promotion getPromoPKByEtudiant(HttpServletRequest request){
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Etudiant etudiant = auth.getNoEtudiant();
		return etudiant.getPromotion();
	}
	
}