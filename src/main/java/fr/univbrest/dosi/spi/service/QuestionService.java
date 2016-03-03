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
			if(qualifRepo.exists(question.getIdQualificatif().getIdQualificatif())){
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
		questRepo.delete(question);
	}
	/**
	 * La méthode de suppression d'une question par idQuestion
	 * @param idQuestion
	 */
	public void deleteQuestionById(Long idQuestion){
		questRepo.delete(idQuestion);
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
	 * Cette méthode retourne un qualificatif 
	 * @param idQualificatif
	 * @return
	 */
	public Question getQuestion(Long idQuestion){
		return questRepo.findOne(idQuestion);
	}
	
}
