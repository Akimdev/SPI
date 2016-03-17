package fr.univbrest.dosi.spi.bean.utils;

import fr.univbrest.dosi.spi.bean.Qualificatif;
import fr.univbrest.dosi.spi.bean.Question;

public class QuesQual {
	private Qualificatif qualificatif;
	private Question question;
	
	public QuesQual() {	
	}
	
	public Qualificatif getQualificatif() {
		return qualificatif;
	}
	
	public void setQualificatif(Qualificatif qualificatif) {
		this.qualificatif = qualificatif;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	
	public QuesQual(Qualificatif qualificatif, Question question) {
		super();
		this.qualificatif = qualificatif;
		this.question = question;
	}
}