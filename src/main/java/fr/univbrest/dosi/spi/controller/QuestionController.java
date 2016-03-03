package fr.univbrest.dosi.spi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.service.QuestionService;
/**
 * Cette classe représente la partie controlleur de la gestion CRUD des questions standards
 * @author Othman
 *
 */
@RestController
public class QuestionController {

	@Autowired
	QuestionService questServ;
	/**
	 * 
	 * @param idQuestion
	 */
	@RequestMapping(value="/supprimerQuestionBis", headers="Accept=application/json")
	public void suppressionQuestionByIdBiss(@RequestParam("idQuestion") Long idQuestion){
		questServ.deleteQuestionById(idQuestion);
	}
	/**
	 * Cette méthode réalise l'ajout d'une nouvelle question 
	 * @param question
	 */
	@RequestMapping(value = "/ajouterQuestion", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE}, produces =  { MediaType.APPLICATION_JSON_VALUE})
	public void ajoutQuestion(@RequestBody final Question question){
		questServ.addQuestion(question);
	}
	/**
	 * Cette méthode modifie une question
	 * @param question
	 */
	@RequestMapping(value="/modifierQuestion", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public void modifyQuestion(@RequestBody final Question question){
		questServ.modifyQuestion(question);
	}
	/**
	 * Cette méthode supprime une question suivant un objet question
	 * @param question
	 */
	@RequestMapping(value="/supprimerQuestion")
	public void suppressionQuestion(Question question){
		questServ.deleteQuestion(question);
	}
	/**
	 * Cette méthode supprime une question suivant un Id
	 * @param idQuestion
	 */
	@RequestMapping(value="/supprimerQuestionAvecId-{idQuestion}")
	public void suppressionQuestionById(@PathVariable(value = "idQuestion")Long idQuestion){
		questServ.deleteQuestionById(idQuestion);
	}
	/**
	 * Cette méthode retourne une liste de questions non ordonnées
	 * @return
	 */
	@RequestMapping(value="/listerQuestions")
	public List<Question> listerQuestion(){
		return questServ.listeQuestion();
	}
}
