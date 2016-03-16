package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;
import fr.univbrest.dosi.spi.bean.QuestionEvaluation;
import fr.univbrest.dosi.spi.bean.RubriqueEvaluation;

public class QuestionEvaluationUtil {

	private QuestionEvaluation questionEvaluation;
	private Question question;
	private Qualificatif qualificatif;
	private RubriqueEvaluation rubriqueEvaluation;
	public QuestionEvaluationUtil() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuestionEvaluationUtil(QuestionEvaluation questionEvaluation,
			Question question, Qualificatif qualificatif,
			RubriqueEvaluation rubriqueEvaluation) {
		super();
		this.questionEvaluation = questionEvaluation;
		this.question = question;
		this.qualificatif = qualificatif;
		this.rubriqueEvaluation = rubriqueEvaluation;
	}
	public QuestionEvaluation getQuestionEvaluation() {
		return questionEvaluation;
	}
	public void setQuestionEvaluation(QuestionEvaluation questionEvaluation) {
		this.questionEvaluation = questionEvaluation;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Qualificatif getQualificatif() {
		return qualificatif;
	}
	public void setQualificatif(Qualificatif qualificatif) {
		this.qualificatif = qualificatif;
	}
	public RubriqueEvaluation getRubriqueEvaluation() {
		return rubriqueEvaluation;
	}
	public void setRubriqueEvaluation(RubriqueEvaluation rubriqueEvaluation) {
		this.rubriqueEvaluation = rubriqueEvaluation;
	}
	
	
}
