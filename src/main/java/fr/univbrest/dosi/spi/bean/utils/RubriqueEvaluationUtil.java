package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.Evaluation;
import fr.univbrest.dosi.spi.bean.Rubrique;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;

/**
 * @author LAKRAA
 *	Bean utilitaire (Rubrique évaluation avec Evaluation et rubrique )
 */
public class RubriqueEvaluationUtil {
	
	private RubriqueEvaluation rubriqueEvaluation;
	private Evaluation evaluation;
	private Rubrique rubrique;
	
	public RubriqueEvaluationUtil() {
		super();
	}	
	
	public RubriqueEvaluationUtil(RubriqueEvaluation rubriqueEvaluation,
			Evaluation evaluation, Rubrique rubrique) {
		super();
		this.rubriqueEvaluation = rubriqueEvaluation;
		this.evaluation = evaluation;
		this.rubrique = rubrique;
	}

	public RubriqueEvaluation getRubriqueEvaluation() {
		return rubriqueEvaluation;
	}

	public void setRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		this.rubriqueEvaluation = rubriqueEvaluation;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Rubrique getRubrique() {
		return rubrique;
	}

	public void setRubrique(Rubrique rubrique) {
		this.rubrique = rubrique;
	}
	
	
}
