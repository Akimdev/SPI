package fr.univbrest.dosi.spi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.dao.EvaluationRepository;
import fr.univbrest.dosi.spi.dao.RubriqueEvaluationRepository;
import fr.univbrest.dosi.spi.exception.SPIException;

/**
 * 
 * @author LAKRAA
 * Classe qui contient les méthodes CRUD
 *
 */
@Service
public class RubriqueEvaluationService {

	@Autowired
	RubriqueEvaluationRepository rubriqueEvaluationRepo;

	@Autowired
	EvaluationRepository evaluationRepo;
	/**
	 * @author LAKRAA
	 * ajouter une nouvelle rubrique evaluation
	 * @param rubriqueEvaluation
	 */
	public RubriqueEvaluation addRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		return rubriqueEvaluationRepo.save(rubriqueEvaluation);
	}

	/**
	 * 	 
	 *  @author LAKRAA
	 * mettre à jour d'une rubrique évaluation
	 * @param rubriqueEvaluation
	 */
	public RubriqueEvaluation updateRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		return rubriqueEvaluationRepo.save(rubriqueEvaluation);
	}

	/**
	 * 	 @author LAKRAA
	 * supprimer une rubrique évaluation
	 * @param idRubriqueEvaluation
	 */
	public void deleteRubriqueEvaluation(Long idRubriqueEvaluation) {
		try {
			rubriqueEvaluationRepo.delete(idRubriqueEvaluation);
		} catch (Exception e) {
			throw new SPIException("La rubrique ne peut pas être supprimée !");
		}
	}

	/**
	 * @author LAKRAA
	 * lister ensemble de rubrique évaluations
	 * @return list rubrique évaluation
	 */
	public List<RubriqueEvaluation> getAllRubriquesEvaluation() {
		List<RubriqueEvaluation> listeRubriquesEvae = (List<RubriqueEvaluation>) rubriqueEvaluationRepo.findAll();
		Collections.sort(listeRubriquesEvae, new Comparator<RubriqueEvaluation>() {
			public int compare(final RubriqueEvaluation qe1,
					final RubriqueEvaluation qe2) {
				return ("" + qe1.getOrdre()).compareTo("" + qe2.getOrdre());
			}
		});
		return listeRubriquesEvae;
	}

	/**
	 * @author LAKRAA
	 * retourner une rubrique évaluation
	 * @param idRubriqueEvaluation
	 * @return
	 * 	une évaluation 
	 */
	public RubriqueEvaluation getRubriqueEvaluation(Long idRubriqueEvaluation) {
		return rubriqueEvaluationRepo.findOne(idRubriqueEvaluation);
	}

	/**
	 * @author LAKRAA
	 * compter le nombre de rubrique évaluation
	 * @return
	 */
	public int nombreRubriquesEvaluation() {
		return (int) rubriqueEvaluationRepo.count();
	}
	
	/**
	 * @author Othman
	 * @param idEvaluation
	 * 
	 * retourne une liste de rubrique d'évaluation concernant une évaluation donnée
	 * @return
	 */
	public List<RubriqueEvaluation> getRubriqueEvaluationsByEvaluation(Integer idEvaluation){
		Evaluation evaluation = evaluationRepo.findOne(idEvaluation);
		List<RubriqueEvaluation> listeRubEva = rubriqueEvaluationRepo.findByIdEvaluation(evaluation);
		Collections.sort(listeRubEva, new Comparator<RubriqueEvaluation>() {
			public int compare(final RubriqueEvaluation re1,
					final RubriqueEvaluation re2) {
				return ("" + re1.getOrdre()).compareTo("" + re2.getOrdre());
			}
		});
		return listeRubEva;
	}
	
	/**
	 * @author Othman
	 * @param idRubriqueEvaluation
	 * 
	 * retourne une liste de questions incluses dans une évaluation
	 * @return
	 */
	public List<QuestionEvaluation> getQuestionEvaluationByRubriqueEvaluation(Long idRubriqueEvaluation){
		RubriqueEvaluation rubEva = rubriqueEvaluationRepo.findOne(idRubriqueEvaluation);
		List<QuestionEvaluation> listeQuestEva = (List<QuestionEvaluation>) rubEva.getQuestionEvaluationCollection();
		Collections.sort(listeQuestEva, new Comparator<QuestionEvaluation>() {
			public int compare(final QuestionEvaluation qe1,
					final QuestionEvaluation qe2) {
				return ("" + qe1.getOrdre()).compareTo("" + qe2.getOrdre());
			}
		});
		return listeQuestEva;
	}
}
