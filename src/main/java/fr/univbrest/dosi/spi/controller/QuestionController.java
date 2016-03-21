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
import fr.univbrest.dosi.spi.bean.utils.QuesQual;
import fr.univbrest.dosi.spi.service.QualificatifService;
import fr.univbrest.dosi.spi.service.QuestionService;

/**
 * Cette classe représente la partie controlleur de la gestion CRUD des questions standards
 *
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
	 *
	 * @param question
	 */
	 
	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST, headers = "Accept=application/json")
	public void addQuestion(@RequestBody QuesQual quesQual) {
		/** récupération de la question à créer ! */
		Question ques = quesQual.getQuestion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Qualificatif qual = qualificatifService.getQualificatif(quesQual.getQualificatif().getIdQualificatif());
		/** ajout de la question */
		ques.setIdQualificatif(qual);
		questServ.addQuestion(ques);
	}

	/**
	 * @author Kenza ABOUAKIL permet de retourner la valeur de MaxIdQuestion pour généré un nouveau ID au qualificatif
	 * @return l'IdQualificatif maximal pour tous les qualificatifs
	 */
	 
	@RequestMapping(value = "/getMaxIdQuestion")
	public Integer getMaxIdQualificatif() {
		return questServ.getMaxIdQuestion();
	}

	/**
	 * @author Youssef Retourne le qualificatif correspondant
	 */
	 
	@RequestMapping(value = "/getQualificatif/{idQuestion}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public Qualificatif getQualificatif(@PathVariable(value = "idQuestion") Long idQuestion) {
		return questServ.getQualificatif(idQuestion);
	}

	/**
	 * Retourne une question par ID
	 */
	 
	@RequestMapping(value = "/getQuestionById/{idQuestion}", produces = { MediaType.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public Question getQuestionById(@PathVariable(value = "idQuestion") Long idQuestion) {
		return questServ.getQuestionById(idQuestion);
	}

	/**
	 * Cette méthode retourne une liste de questions non ordonnées
	 *
	 * @return
	 */
	@RequestMapping(value = "/questions")
	public List<Question> listerQuestion() {
		return questServ.getAllQuestions();
	}

	@RequestMapping(value = "/nombreQuestions")
	public int nombreQuestions() {
		return questServ.nombreQuestions();
	}

	/**
	 * Cette méthode supprime une question suivant un objet question
	 *
	 * @param question
	 */
	@RequestMapping(value = "/deleteQuestion")
	public void suppressionQuestion(Question question) {
		questServ.deleteQuestion(question);
	}

	/**
	 * Cette méthode supprime une question suivant un Id
	 *
	 * @param idQuestion
	 */
	@RequestMapping(value = "/deleteQuestionById-{idQuestion}")
	public void suppressionQuestionById(@PathVariable(value = "idQuestion") Long idQuestion) {
		questServ.deleteQuestionById(idQuestion);
	}

	/**
	 * Cette méthode modifie une question
	 *
	 * @param question
	 */
	@RequestMapping(value = "/updateQuestion", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void updateQuestion(@RequestBody final QuesQual quesQual) {
		/** récupération de la question à modifier ! */
		Question ques = quesQual.getQuestion();
		/** récupération des objets à partir de leur id envoyer du JSON */
		Qualificatif qual = qualificatifService.getQualificatif(quesQual.getQualificatif().getIdQualificatif());
		/** modification de la question */
		ques.setIdQualificatif(qual);
		questServ.updateQuestion(ques);
	}
}