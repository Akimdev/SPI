package fr.univbrest.dosi.spi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.dao.EvaluationRepository;
import fr.univbrest.dosi.spi.exception.SPIException;
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
	
	public void addEvaluation(Evaluation e){
		evaRepo.save(e);
	}
	
	public void deleteEvaluation(Long idEvaluation){
		Evaluation evaluation = evaRepo.findByIdEvaluation(idEvaluation);
		evaRepo.delete(evaluation);
	}
}
