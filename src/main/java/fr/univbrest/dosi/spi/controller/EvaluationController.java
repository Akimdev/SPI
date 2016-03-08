package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
