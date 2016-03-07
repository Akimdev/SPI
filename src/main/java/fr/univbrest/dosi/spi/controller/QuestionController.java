package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.utils.QuesQual;
import fr.univbrest.dosi.spi.service.QualificatifService;
import fr.univbrest.dosi.spi.service.QuestionService;
/**
 * Cette classe représente la partie controlleur de la gestion CRUD des questions standards
 * @author Othman
 * @author hakim
 *
 */
@RestController
public class QuestionController {
	
	@Autowired
	QualificatifService qualificatifService;
	@Autowired
	QuestionService questServ;
	
	
	/**
	 * Cette méthode réalise l'ajout d'une nouvelle question 
	 * @param question
	 */
	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addQuestion(@RequestBody  QuesQual quesQual){
		/** récupération de la question à créer ! */
		Question ques = quesQual.getQuestion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Qualificatif qual = qualificatifService.getQualificatif(quesQual.getQualificatif().getIdQualificatif());
		/** ajout de la question */
		ques.setIdQualificatif(qual);
		quesQual.setQualificatif(qual);
		questServ.addQuestion(ques);
	}
	/**
	 * Cette méthode modifie une question
	 * @param question
	 */
	@RequestMapping(value="/updateQuestion", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void updateQuestion(@RequestBody final QuesQual quesQual){
		/** récupération de la question à créer ! */
		Question ques = quesQual.getQuestion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Qualificatif qual = qualificatifService.getQualificatif(quesQual.getQualificatif().getIdQualificatif());
		/** ajout de la question */
		ques.setIdQualificatif(qual);
		quesQual.setQualificatif(qual);
		questServ.updateQuestion(ques);
	}
	/**
	 * Cette méthode supprime une question suivant un objet question
	 * @param question
	 */
	@RequestMapping(value="/deleteQuestion")
	public void suppressionQuestion(Question question){
		questServ.deleteQuestion(question);
	}
	/**
	 * Cette méthode supprime une question suivant un Id
	 * @param idQuestion
	 */
	@RequestMapping(value="/deleteQuestionById-{idQuestion}")
	public void suppressionQuestionById(@PathVariable(value = "idQuestion")Long idQuestion){
		questServ.deleteQuestionById(idQuestion);
	}
	/**
	 * Cette méthode retourne une liste de questions non ordonnées
	 * @return
	 */
	@RequestMapping(value="/getAllQuestions")
	public List<Question> listerQuestion(){
		return questServ.getAllQuestions();
	}
	/**
	 * Retourne une question par ID
	 */
	@RequestMapping(value="/getQuestionById/{idQuestion}")
	public Question getQuestionById(@PathVariable(value="idQuestion") Long idQuestion){
		return questServ.getQuestionById(idQuestion);
	}
	
	@RequestMapping(value="/lenghtQuestion")
	public int nombreQuestions(){
		return questServ.nombreQuestions();
	}
}
