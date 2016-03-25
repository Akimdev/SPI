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
import fr.univbrest.dosi.spi.bean.CgRefCodes;
import fr.univbrest.dosi.spi.bean.Enseignant;
import fr.univbrest.dosi.spi.bean.Etudiant;
import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.PromotionPK;
import fr.univbrest.dosi.spi.service.DomainesSevices;
import fr.univbrest.dosi.spi.service.EvaluationService;

/**
 * 
 * @author Othman
 *
 */
 
@RestController
public class EvaluationController {

	@Autowired
	EvaluationService evaServ;
	
	@Autowired
	DomainesSevices domaineService;
	
	
	@RequestMapping(value="/evaluations")
	public List<Evaluation> listerEvaluations(){
		
		for(int nbr=0; nbr<evaServ.nombreEvaluations();nbr++){
			Evaluation evaluation =evaServ.getAllEvaluations().get(nbr);
			String etat =evaluation.getEtat();
			CgRefCodes domaine = domaineService.getRvMainingByRvAbbreviation(etat);
			String etatEvaluation = domaine.getRvMeaning();
			evaluation.setEtat(etatEvaluation);	
		}
		return evaServ.getAllEvaluations();
	}
	
	@RequestMapping(value="/findEvaluationById-{idEvaluation}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Evaluation getEvaluation(@PathVariable(value="idEvaluation")Integer idEvaluation){
		return evaServ.getEvaluation(idEvaluation);
	}

	@RequestMapping(value="/addEvaluation", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void addEvaluation(@RequestBody final Evaluation e, HttpServletRequest request){
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant ens = auth.getNoEnseignant();
		e.setNoEnseignant(ens);
		evaServ.addEvaluation(e);
	}
	
	@RequestMapping(value="/deleteEvaluation-{idEvaluation}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public void deleteEvaluation(@PathVariable(value="idEvaluation")Integer idEvaluation){
		evaServ.deleteEvaluation(idEvaluation);
	}
	
	@RequestMapping(value="/updateEvaluation", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody void updateEvaluation(@RequestBody Evaluation evaluation, HttpServletRequest request){
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant ens = auth.getNoEnseignant();
		evaluation.setNoEnseignant(ens);
		evaServ.updateEvaluation(evaluation);
	}
	
	/**
	 * @author Othman controlleur pour retourner le nombre de formations
	 */
	@RequestMapping(value = "/nombreEvaluations", headers = "Accept=application/json")
	public long nombreEvaluations() {
		return evaServ.nombreEvaluations();
	}
	
	/**
	 * @author Othman controlleur pour retourner le nombre de formations
	 */
	@RequestMapping(value = "/nombreEvaluationsEnseignant", headers = "Accept=application/json")
	public long nombreEvaluationsEnseignant(HttpServletRequest request) {
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant enseignant = auth.getNoEnseignant();
		return evaServ.getEvaluationsEnseignant(enseignant).size();
	}
	/**
	 * @author Othman
	 * @param promotionPK
	 * @return  le nombre d'évaluations concernant une promotion donnée
	 */
	@RequestMapping(value="/nombreEvaluationsPromo",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=application/json")
	public int nombreEvaluationsPromo(@RequestBody PromotionPK promotionPK){
		return evaServ.nombreEvaluationsPromo(promotionPK.getCodeFormation(), promotionPK.getAnneeUniversitaire());
	}
	
	/**
	 * @author Othman
	 * @param promotionPK
	 * @return liste d'évaluations concernant une promotion
	 */
	@RequestMapping(value="/getEvaluationsPromo",method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, headers = "Accept=application/json")
	public List<Evaluation> getEvaluationsPromo(@RequestBody PromotionPK promotionPK){
		return evaServ.getEvaluationsPromo(promotionPK.getCodeFormation(), promotionPK.getAnneeUniversitaire());
	}
	
	/**
	 * @author Othman
	 * @param request
	 * @return liste des évaluations concernant un enseignant
	 */
	@RequestMapping(value="/evaluationsEnseignant", headers = "Accept=application/json")
	public List<Evaluation> getEvaluationsEnseignant(HttpServletRequest request){
		Authentification auth = (Authentification) request.getSession().getAttribute("user");
		Enseignant enseignant = auth.getNoEnseignant();
		return evaServ.getEvaluationsEnseignant(enseignant);
		}
	}