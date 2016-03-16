package fr.univbrest.dosi.spi.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
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

	/**
	 * @author LAKRAA
	 * ajouter une nouvelle rubrique evaluation
	 * @param rubriqueEvaluation
	 */
	public void addRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		rubriqueEvaluationRepo.save(rubriqueEvaluation);
	}

	/**
	 * 	 
	 *  @author LAKRAA
	 * mettre à jour d'une rubrique évaluation
	 * @param rubriqueEvaluation
	 */
	public void updateRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		rubriqueEvaluationRepo.save(rubriqueEvaluation);
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

}
