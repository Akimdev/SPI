package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.dao.EvaluationRepository;
/**
 * 
 * @author Othman
 *
 */
@Service
public class EvaluationService {
	
	@Autowired
	EvaluationRepository evaRepo;
	
	public List<Evaluation> getAllEvaluations(){
		return (List<Evaluation>) evaRepo.findAll();
	}
	
	public Evaluation getEvaluation(Long idEvaluation){
		return evaRepo.findByIdEvaluation(idEvaluation);
	}
}

