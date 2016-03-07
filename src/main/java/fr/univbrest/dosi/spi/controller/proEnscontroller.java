package fr.univbrest.dosi.spi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Formation;
import fr.univbrest.dosi.spi.bean.ProEns;
import fr.univbrest.dosi.spi.bean.Promotion;
import fr.univbrest.dosi.spi.service.EnseignantService;
import fr.univbrest.dosi.spi.service.FormationService;
import fr.univbrest.dosi.spi.service.PromotionService;

@RestController
public class proEnscontroller {
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	FormationService formationService;
	
	@Autowired
	EnseignantService enseignantService;
	
	@RequestMapping(value="/testPro", method = RequestMethod.POST,  headers="Accept=application/json")
	public @ResponseBody String addTest(@RequestBody ProEns proEns){
		Promotion pro = proEns.getPromotion();
		Enseignant ens = proEns.getEnseignant();
		Formation form = proEns.getFormation();
		Promotion promotion = proEns.getPromotion();
		
		pro.setNoEnseignant(ens);
		pro.setFormation(form);
		
		//promotionService.addPromotion(pro);
		
		//Formation exis = formationService.getFormation(proEns.getFormation().getCodeFormation());
		
		/* on ajout la formation qu'on récupère du super objet */
		//formationService.addFormation(form);
	
	/** récupération des objets à partir de leur id envoyer du JSON */
		Formation formationExistante = formationService.getFormation(proEns.getFormation().getCodeFormation());
		Enseignant enseignantExistante = enseignantService.getEnseignant(proEns.getEnseignant().getNoEnseignant());
		System.out.println("formation existante :"+formationExistante.getNomFormation());
		System.out.println("enseignant existant :"+enseignantExistante.getNom());
	/**                                                              */
		
	/** construction de l'objet promotion avec formation et enseignant reçu de JSON */
		promotion.setNoEnseignant(enseignantExistante);
		promotion.setFormation(formationExistante);
		System.out.println("promotionPk code for :"+promotion.getPromotionPK().getCodeFormation());
		System.out.println("promotionPk annee :"+promotion.getPromotionPK().getAnneeUniversitaire());
		System.out.println("enseignant de promotion :"+promotion.getNoEnseignant().getNom());
		System.out.println("formation de promotion :"+promotion.getFormation().getNomFormation());
	/**	                                                             */
	   
	   promotionService.addPromotion(promotion);
	
		
		//System.out.println(exis.getCodeFormation()+" "+exis.getNomFormation());
		
		System.out.println("processus pro ="+pro.getProcessusStage()+" "+ens.getNom());
		System.out.println("formation de pro= "+pro.getFormation().getCodeFormation());
		System.out.println("ens de pro ="+pro.getNoEnseignant().getNom());
		
		return pro.getProcessusStage()+" "+ens.getNom()+" "+pro.getNoEnseignant().getNom()+
			   pro.getFormation().getCodeFormation();
	}
}
