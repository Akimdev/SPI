package fr.univbrest.dosi.spi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.dao.QuestionEvaluationRepository;

@Service
public class QuestionEvaluationService {

	@Autowired
	QuestionEvaluationRepository questionEvaluationRepo;
	
	
	
	public QuestionEvaluation addQuestionEvaluation(QuestionEvaluation questionEvaluation){
			return questionEvaluationRepo.save(questionEvaluation);
	}
	
	public void updateQuestionEvaluation(QuestionEvaluation questionEvaluation){
		questionEvaluationRepo.save(questionEvaluation);
	}
	
	public void deleteQuestionEvaluation(Long idQuestionEvaluation){
		questionEvaluationRepo.delete(idQuestionEvaluation);
	}
	
	public List<QuestionEvaluation> getAllQuestionsEvaluation(){
		List<QuestionEvaluation> listeQEs = (List<QuestionEvaluation>) questionEvaluationRepo.findAll();
		Collections.sort(listeQEs, new Comparator<QuestionEvaluation>() {
		        public int compare(final QuestionEvaluation qe1, final QuestionEvaluation qe2) {
		            return (""+qe1.getOrdre()).compareTo(""+qe2.getOrdre());
		        }
		       });
		return listeQEs;
	}
	
	public QuestionEvaluation getQuestionEvaluation(Long idQuestionEvaluation){
		return questionEvaluationRepo.findOne(idQuestionEvaluation);
	}
	
	public int nombreQuestionsEvaluation(){
		return (int) questionEvaluationRepo.count();
	}
	
	
}