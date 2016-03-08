package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Evaluation;
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
	
	@RequestMapping(value="/evaluations")
	public List<Evaluation> listerEvaluations(){
		return evaServ.getAllEvaluations();
	}
	
	/**
	 * @author LAKRAA
	 * Méthode qui permet de recuperer les avaluations par ID
	 */
	
	@RequestMapping(value = "/evaluation/{idEvaluation}", produces = { MediaType.APPLICATION_JSON_VALUE }, headers="Accept: application/hal+json")
	public  List<Evaluation> getEvalById(
			@PathVariable(value = "idEvaluation") Long idEvaluation) {
		return evaServ.getEvaById(idEvaluation);
	}
}
