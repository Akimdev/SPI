package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.QuestionEvaluationUtil;
import fr.univbrest.dosi.spi.service.QualificatifService;
import fr.univbrest.dosi.spi.service.QuestionEvaluationService;
import fr.univbrest.dosi.spi.service.QuestionService;
import fr.univbrest.dosi.spi.service.RubriqueEvaluationService;

@RestController
public class QuestionEvaluationController {

	@Autowired
	QuestionEvaluationService questionEvaluationServ;
	
	@Autowired
	QuestionService questionServ;
	
	@Autowired
	QualificatifService qualificatifServ;
	
	@Autowired
	RubriqueEvaluationService rubriqueEvaluationSer;
	
	@RequestMapping(value="/addQuestionEvaluation" , method = RequestMethod.POST, headers= "Accept=application/json")
	public QuestionEvaluation addQuestionEvaluation(@RequestBody final QuestionEvaluationUtil questionEvaluationUtil){
		
		QuestionEvaluation questionEvaluation = questionEvaluationUtil.getQuestionEvaluation();
		Question question = questionServ.getQuestion(questionEvaluationUtil.getQuestion().getIdQuestion());
		questionEvaluation.setIdQuestion(question);
		Qualificatif qualificatif = question.getIdQualificatif();
		questionEvaluation.setIdQualificatif(null);
		RubriqueEvaluation rubriqueEvaluation= rubriqueEvaluationSer.getRubriqueEvaluation(questionEvaluationUtil.getRubriqueEvaluation().getIdRubriqueEvaluation());
		questionEvaluation.setIdRubriqueEvaluation(rubriqueEvaluation);
		return questionEvaluationServ.addQuestionEvaluation(questionEvaluation);
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
