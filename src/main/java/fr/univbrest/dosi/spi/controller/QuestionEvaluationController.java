package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.QuestionEvaluationUtil;
import fr.univbrest.dosi.spi.service.QuestionEvaluationService;
import fr.univbrest.dosi.spi.service.QuestionService;

@RestController
public class QuestionEvaluationController {

	@Autowired
	QuestionEvaluationService questionEvaluationServ;
	
	@Autowired
	QuestionService questionServ;
	

	
	@RequestMapping(value="/addQuestionEvaluation" , method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String addQuestionEvaluation(@RequestBody final QuestionEvaluationUtil questionEvaluationUtil){
		QuestionEvaluation questionEvaluation = questionEvaluationServ.getQuestionEvaluation(questionEvaluationUtil.getQuestionEvaluation().getIdQuestionEvaluation());
		Question question = questionServ.getQuestion(questionEvaluationUtil.getQuestion().getIdQuestion());
		questionEvaluation.setIdQuestion(question);
		questionEvaluation.setIdQualificatif(null);
		//questionEvaluation.setIdRubriqueEvaluation(rubriqueEvaluation);
		questionEvaluationServ.addQuestionEvaluation(questionEvaluation);
		return "succes";
	}
	
	@RequestMapping(value="/updateQuestionEvaluation" , method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public String updateQuestionEvaluation(@RequestBody final QuestionEvaluation questionEvaluation){
		questionEvaluationServ.updateQuestionEvaluation(questionEvaluation);
		return "succes";
	}
	
	@RequestMapping(value="/deleteQuestionEvaluation-{idQuestionEvaluation}" , method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteQuestionEvaluation(@PathVariable("idQuestionEvaluation")Long idQuestionEvaluation){
		questionEvaluationServ.deleteQuestionEvaluation(idQuestionEvaluation);
	}
	
	@RequestMapping(value="/getAllQuestionsEvaluation", headers = "Accept=application/json")
	public List<QuestionEvaluation> getAllQuestionsEvaluation(){
		return questionEvaluationServ.getAllQuestionsEvaluation();
	}
	
	@RequestMapping(value="/nombreQuestionsEvaluation", headers = "Accept=application/json")
	public int nombreQuestionsEvaluation(){
		return questionEvaluationServ.nombreQuestionsEvaluation();
	}
}
