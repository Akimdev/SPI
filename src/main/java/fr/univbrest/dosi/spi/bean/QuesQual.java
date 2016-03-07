package fr.univbrest.dosi.spi.bean;

import org.codehaus.jackson.annotate.JsonProperty;

public class QuesQual {
	
	private Qualificatif qualificatif;
	private Question question;
	
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
	public QuesQual() {
		
	}
	
	

}
