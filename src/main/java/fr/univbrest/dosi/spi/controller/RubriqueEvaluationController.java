package fr.univbrest.dosi.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;
import fr.univbrest.dosi.spi.bean.utils.RubriqueEvaluationUtil;
import fr.univbrest.dosi.spi.service.EvaluationService;
import fr.univbrest.dosi.spi.service.RubriqueEvaluationService;
import fr.univbrest.dosi.spi.service.RubriqueService;

/**
 * Classe controller de Rubrique évaluation
 * @author LAKRAA
 *
 */

@RestController
public class RubriqueEvaluationController {

	@Autowired
	RubriqueEvaluationService rubriqueEvaluationServ;
	
	@Autowired
	EvaluationService evaluationServ;
	
	@Autowired
	RubriqueService rubriqueServ;
	
	/**
	 * @author LAKRAA
	 * @return ajouter une nouvelle rubrique évaluation
	 */
	@RequestMapping(value="/addRubriqueEvaluation" , method = RequestMethod.POST, headers= "Accept=application/json")
	public RubriqueEvaluation addRubriqueEvaluation(@RequestBody final RubriqueEvaluationUtil rubriqueEvaluationUtil){
		RubriqueEvaluation rubriqueEvaluation = rubriqueEvaluationUtil.getRubriqueEvaluation();
		Evaluation evaluation = evaluationServ.getEvaluation(rubriqueEvaluationUtil.getEvaluation().getIdEvaluation());
		rubriqueEvaluation.setIdEvaluation(evaluation);
		Rubrique rubrique = rubriqueServ.getRubrique(rubriqueEvaluationUtil.getRubrique().getIdRubrique());
		rubriqueEvaluation.setIdRubrique(rubrique);
		return rubriqueEvaluationServ.addRubriqueEvaluation(rubriqueEvaluation);
	}
	
	/**
	 * @author LAKRAA
	 * @param idQuestionEvaluation
	 */
	@RequestMapping(value="/deleteRubriqueEvaluation-{idRubriqueEvaluation}" , method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public void deleteRubriqueEvaluation(@PathVariable("idRubriqueEvaluation")Long idRubriqueEvaluation){
		rubriqueEvaluationServ.deleteRubriqueEvaluation(idRubriqueEvaluation);
	}
	
	/**
	 * @author LAKRAA
	 * @return la liste des rubriques évaluation
	 */
	@RequestMapping(value="/getAllRubriquesEvaluation", headers = "Accept=application/json")
	public List<RubriqueEvaluation> getAllRubriqueEvaluation(){
		return rubriqueEvaluationServ.getAllRubriquesEvaluation();
	}
	
	/**
	 * @author LAKRAA
	 * @return Le nombre de rubrique évaluation
	 */
	@RequestMapping(value="/nombreRubriquesEvaluation", headers = "Accept=application/json")
	public int nombreRubriquesEvaluation(){
		return rubriqueEvaluationServ.nombreRubriquesEvaluation();
	}
	/**
	 * @author Othman
	 * @param idEvaluation
	 * retourne une liste de rubrique d'évaluation concernant une évaluation donnée
	 * @return
	 */
	@RequestMapping(value="/getRubriqueEvaluationByEvaluation-{idEvaluation}", headers = "Accept=application/json")
	public List<RubriqueEvaluation> getRubriqueEvaluationByEvaluation(@PathVariable("idEvaluation") Integer idEvaluation){
		return rubriqueEvaluationServ.getRubriqueEvaluationsByEvaluation(idEvaluation);
	}
	
	/**
	 * @author Othman
	 * @param idRubriqueEvaluation
	 * retourne une liste de questions incluses dans une évaluation
	 * @return
	 */
	@RequestMapping(value="/getQuestionEvaluationByEvaluation-{idRubriqueEvaluation}", headers = "Accept=application/json")
	public List<QuestionEvaluation> getQuestionEvaluationByRubriqueEvaluation(@PathVariable("idRubriqueEvaluation")Long idRubriqueEvaluation){
		return rubriqueEvaluationServ.getQuestionEvaluationByRubriqueEvaluation(idRubriqueEvaluation);
	}
}
