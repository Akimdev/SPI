package fr.univbrest.dosi.spi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.dao.QualificatifRepository;
import fr.univbrest.dosi.spi.dao.QuestionRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * 
 * @author Othman
 *
 *Cette classe représente la partie service de la gestion de CRUD des questions standards
 */
@Service
public class QuestionService {
	@Autowired 
	QuestionRepository questRepo;
	@Autowired
	QualificatifRepository qualifRepo;
	/**
	 * La méthode pour ajouter une question
	 * @param question
	 */
	public void addQuestion(Question question){
			if(qualifRepo.exists(question.getIdQualificatif().getIdQualificatif()) && (!(questRepo.exists(question.getIdQuestion())))){
				Qualificatif qualif = qualifRepo.findOne(question.getIdQualificatif().getIdQualificatif());
				question.setIdQualificatif(qualif);
				questRepo.save(question);
			}
			else{
				throw new SPIException("Echec de l'ajout d'une nouvelle question, Qualificatif inexistant");
			}
	}
	/**
	 * La méthode pour modifier une question
	 * @param question
	 */
	public void modifyQuestion(Question question){
		questRepo.save(question);
	}
	/**
	 * La méthode de suppression d'une question par objet Question
	 * @param question
	 */
	public void deleteQuestion(Question question){
		if(question.getIdQualificatif()==null){
		questRepo.delete(question);
		}
		else{
			throw new SPIException("La question ne peut pas être supprimée. Déselectionnez la avant d'une évaluation ou son qualificatif");
		}
	}
	/**
	 * La méthode de suppression d'une question par idQuestion
	 * @param idQuestion
	 */
	public void deleteQuestionById(Long idQuestion){
		if(questRepo.findOne(idQuestion).getIdQualificatif()==null){
			questRepo.delete(idQuestion);
			}
			else{
				throw new SPIException("La question ne peut pas être supprimée. Déselectionnez la avant d'une évaluation ou son qualificatif");
			}
	}
	/**
	 * La méthode pour afficher la liste des questions
	 * @return
	 * retourne une liste de questions non ordonnées
	 */
	public List<Question> listeQuestion(){
		List<Question> questionList = new ArrayList<Question>();
		questionList = (List<Question>) questRepo.findAll();
		return questionList;
	}
	/**
	 * @return
	 * Retourne une question par ID
	 */
	public Question getQuestionById(Long idQuestion){
		return questRepo.findOne(idQuestion);
	}
	/** Cette méthode retourne un qualificatif 
	 * @param idQualificatif
	 * @return
	 */
	public Question getQuestion(Long idQuestion){
		return questRepo.findOne(idQuestion);
	}
	/**
	 * Cette méthode retourne le nombre de questions
	 * @return
	 */
	public int nombreQuestions(){
		List<Question> listeQuestions = (List<Question>) questRepo.findAll();
		return listeQuestions.size();
	}	
}
