package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.dao.QuestionRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 *
 * @author Othman
 * @author hakim
 *
 *         Cette classe représente la partie service de la gestion de CRUD des questions standards
 */
@Service
public class QuestionService {
	@Autowired
	QuestionRepository questRepo;

	/**
	 * La méthode pour ajouter une question
	 *
	 * @param question
	 */
	public void addQuestion(Question question) {
		if(!questRepo.exists(question.getIdQuestion()))
			questRepo.save(question);
	else
		throw new SPIException("Impossible d'ajouter la question, l'ID utlisé exste déjà dans la BD!");
	}

	/**
	 * La méthode de suppression d'une question par objet Question
	 *
	 * @param question
	 */
	public void deleteQuestion(Question question) {
		try {
			questRepo.delete(question);
		} catch (Exception e) {
			throw new SPIException("La question ne peut pas être supprimée !");
		}
	}

	/**
	 * La méthode de suppression d'une question par idQuestion
	 *
	 * @param idQuestion
	 */
	public void deleteQuestionById(Long idQuestion) {
		try {
			questRepo.delete(questRepo.findOne(idQuestion));
		} catch (Exception e) {
			throw new SPIException("La question ne peut pas être supprimée !");
		}
	}

	/**
	 * La méthode pour afficher la liste des questions
	 *
	 * @return retourne une liste de questions non ordonnées
	 */
	public List<Question> getAllQuestions() {
		List<Question> questionList = new ArrayList<Question>();
		questionList = (List<Question>) questRepo.findAll();
		return questionList;
	}

	/**
	 * @author Kenza ABOUAKIL
	 * @return le max des IdQuestions
	 */
	public Integer getMaxIdQuestion() {
		return questRepo.getMaxIdQuestion();
	}

	/**
	 * @author Youssef Rechercher le qualificatif d'une question
	 * @param idQuestion
	 */
	public Qualificatif getQualificatif(Long idQuestion) {
		return questRepo.findQualificatif(idQuestion);
	}
//	-----------------------------Service à écrire pour récuperer les qualificatifs-------------
//	/**
//	 * @author Zouhair et Kenza Rechercher le qualificatif d'une question
//	 * @param idQuestions
//	 */
//	public List<Qualificatif> getQualificatif(List<Long> idQuestions) {
//		List<Qualificatif> l= new A
//		return questRepo.findQualificatif(idQuestion);
//	}
//	-----------------------------------------------------------------------------------------
	/**
	 * Cette méthode retourne un qualificatif
	 *
	 * @param idQualificatif
	 * @return
	 */
	public Question getQuestion(Long idQuestion) {
		return questRepo.findOne(idQuestion);
	}

	/**
	 * @return Retourne une question par ID
	 */
	public Question getQuestionById(Long idQuestion) {
		return questRepo.findOne(idQuestion);
	}

	/**
	 * Cette méthode retourne le nombre de questions
	 *
	 * @return
	 */
	public int nombreQuestions() {
		List<Question> listeQuestions = (List<Question>) questRepo.findAll();
		return listeQuestions.size();
	}

	/**
	 * La méthode pour modifier une question
	 *
	 * @param question
	 */
	public void updateQuestion(Question question) {
		if(questRepo.exists(question.getIdQuestion()))
				questRepo.save(question);
		else
			throw new SPIException("Impossible de modifier la qustion, La question n'existe pas dans la BD!");
	}
}
